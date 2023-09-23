package com.spiralin.spiralinapp.infrastructure.persistence;

import com.spiralin.spiralinapp.domain.entity.Token;
import com.spiralin.spiralinapp.domain.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class TokenRepositoryImpl implements TokenRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public TokenRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Token save(Token token) {
        return mongoTemplate.save(token);
    }

    @Override
    public Collection<Token> saveAll(Collection<Token> tokens) {
        return tokens.stream().map(mongoTemplate::save).toList();
    }

    @Override
    public Optional<Token> findByToken(String token) {
        Query query = new Query();
        query.addCriteria(Criteria.where("token").is(token));

        return mongoTemplate.find(query, Token.class)
                .stream()
                .findFirst();
    }

    @Override
    public Collection<Token> findAllValidTokenByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userId));

        return mongoTemplate.find(query, Token.class);
    }
}
