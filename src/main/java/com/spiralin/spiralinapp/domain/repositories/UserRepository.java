package com.spiralin.spiralinapp.domain.repositories;

import com.spiralin.spiralinapp.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    Optional<User> findByEmail(String email);
    User save(User user);
}
