package com.spiralin.spiralinapp.domain.services.impl;

import com.spiralin.spiralinapp.domain.repositories.UserRepository;
import com.spiralin.spiralinapp.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository
                        .findByEmail(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException("User not found"));
            }
        };
    }
}
