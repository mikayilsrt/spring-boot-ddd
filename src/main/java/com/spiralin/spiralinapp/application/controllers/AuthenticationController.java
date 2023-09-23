package com.spiralin.spiralinapp.application.controllers;

import com.spiralin.spiralinapp.application.ResponseHelper;
import com.spiralin.spiralinapp.domain.services.AuthenticationService;
import com.spiralin.spiralinapp.domain.valueObjects.request.SignInRequest;
import com.spiralin.spiralinapp.domain.valueObjects.request.SignUpRequest;
import com.spiralin.spiralinapp.domain.valueObjects.response.JwtAuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseHelper<JwtAuthenticationResponse>> signup(
            @Valid @RequestBody SignUpRequest request
    ) {
        try {
            return ResponseHelper.success(authenticationService.signup(request));
        } catch (Exception e) {
            return ResponseHelper.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseHelper<JwtAuthenticationResponse>> signin(
            @RequestBody SignInRequest request
    ) {
        try {
            return ResponseHelper.success(authenticationService.signin(request));
        } catch (Exception e) {
            return ResponseHelper.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseHelper<JwtAuthenticationResponse>> refresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        try {
            return ResponseHelper.success(authenticationService.refresh(request, response));
        } catch (Exception e) {
            return ResponseHelper.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
