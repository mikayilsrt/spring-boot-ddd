package com.spiralin.spiralinapp.infrastructure.persistence;

import com.spiralin.spiralinapp.domain.entity.User;
import com.spiralin.spiralinapp.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        return mongoTemplate
                .find(query, User.class)
                .stream()
                .findFirst();
    }

    @Override
    public User save(User user) {
        return mongoTemplate.save(user);
    }
}
