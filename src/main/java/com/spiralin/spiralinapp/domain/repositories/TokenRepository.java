package com.spiralin.spiralinapp.domain.repositories;

import com.spiralin.spiralinapp.domain.entity.Token;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface TokenRepository {
    Token save(Token token);

    Collection<Token> saveAll(Collection<Token> tokens);

    Optional<Token> findByToken(String token);

    Collection<Token> findAllValidTokenByUserId(String userId);
}
