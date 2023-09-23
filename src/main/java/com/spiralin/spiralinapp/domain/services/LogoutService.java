package com.spiralin.spiralinapp.domain.services;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    @Autowired
    private TokenService tokenService;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        var token = getJWTFromRequest(request);

        if (token.isPresent()) {
            var findToken = tokenService.findByToken(token.get())
                    .orElse(null);
            if (findToken != null) {
                findToken.setExpired(true);
                findToken.setRevoked(true);
                tokenService.save(findToken);
                SecurityContextHolder.clearContext();
            }
        }
    }

    private Optional<String> getJWTFromRequest(HttpServletRequest request) {
        String token = null;
        final String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            token = bearerToken.substring(7);
        }
        return Optional.ofNullable(token);
    }
}
