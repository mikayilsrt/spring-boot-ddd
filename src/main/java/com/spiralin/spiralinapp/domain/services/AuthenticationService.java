package com.spiralin.spiralinapp.domain.services;

import com.spiralin.spiralinapp.domain.valueObjects.request.SignInRequest;
import com.spiralin.spiralinapp.domain.valueObjects.request.SignUpRequest;
import com.spiralin.spiralinapp.domain.valueObjects.response.JwtAuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);

    JwtAuthenticationResponse refresh(HttpServletRequest request, HttpServletResponse response);
}
