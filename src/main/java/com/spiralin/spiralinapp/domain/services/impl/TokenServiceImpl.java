package com.spiralin.spiralinapp.domain.services.impl;

import com.spiralin.spiralinapp.domain.entity.Token;
import com.spiralin.spiralinapp.domain.repositories.TokenRepository;
import com.spiralin.spiralinapp.domain.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Autowired
    private final TokenRepository tokenRepository;

    @Override
    public Token save(Token token) {
        return this.tokenRepository.save(token);
    }

    @Override
    public Collection<Token> saveAll(Collection<Token> tokens) {
        return this.tokenRepository.saveAll(tokens);
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return this.tokenRepository.findByToken(token);
    }

    @Override
    public Collection<Token> findAllValidTokenByUserId(String userId) {
        return this.tokenRepository.findAllValidTokenByUserId(userId);
    }
}
