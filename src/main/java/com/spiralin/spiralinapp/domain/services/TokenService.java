package com.spiralin.spiralinapp.domain.services;

import com.spiralin.spiralinapp.domain.entity.Token;

import java.util.Collection;
import java.util.Optional;

public interface TokenService {
    Token save(Token token);

    Collection<Token> saveAll(Collection<Token> tokens);

    Optional<Token> findByToken(String token);

    Collection<Token> findAllValidTokenByUserId(String userId);
}
