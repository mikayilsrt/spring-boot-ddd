package com.spiralin.spiralinapp.domain.services.impl;

import com.spiralin.spiralinapp.domain.entity.Token;
import com.spiralin.spiralinapp.domain.entity.User;
import com.spiralin.spiralinapp.domain.enums.Role;
import com.spiralin.spiralinapp.domain.enums.TokenType;
import com.spiralin.spiralinapp.domain.exception.AuthenticateException;
import com.spiralin.spiralinapp.domain.exception.DuplicateEmailException;
import com.spiralin.spiralinapp.domain.repositories.UserRepository;
import com.spiralin.spiralinapp.domain.services.AuthenticationService;
import com.spiralin.spiralinapp.domain.services.JwtService;
import com.spiralin.spiralinapp.domain.services.TokenService;
import com.spiralin.spiralinapp.domain.valueObjects.request.SignInRequest;
import com.spiralin.spiralinapp.domain.valueObjects.request.SignUpRequest;
import com.spiralin.spiralinapp.domain.valueObjects.response.JwtAuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final TokenService tokenService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        var userResult = userRepository.findByEmail(user.getEmail());
        if (userResult.isPresent()) {
            throw new DuplicateEmailException("There is already a user registered with the email provided.");
        }

        var savedUser = userRepository.save(user);
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveToken(savedUser, token);

        return JwtAuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new AuthenticateException(e.getMessage());
        }

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveToken(user, token);

        return JwtAuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public JwtAuthenticationResponse refresh(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null && !authHeader.startsWith("Bearer ")) {
            throw new AuthenticateException("Accès refusé. Un jeton Bearer est requis pour accéder à cette ressource.");
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUserName(refreshToken);
        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail).orElseThrow(() -> new AuthenticateException("User not found."));

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveToken(user, accessToken);

                return JwtAuthenticationResponse.builder()
                        .token(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }

        throw new AuthenticateException("Veuillez vous assurer que votre jeton JWT est valide pour accéder à cette ressource.");
    }

    private void saveToken(User savedUser, String token) {
        var tokenObj = Token.builder()
                .token(token)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .user_id(savedUser.getId())
                .build();
        tokenService.save(tokenObj);
    }

    private void revokeAllUserTokens(User user) {
        var results = tokenService.findAllValidTokenByUserId(user.getId());
        if (results.isEmpty())
            return;
        results.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenService.saveAll(results);
    }
}
