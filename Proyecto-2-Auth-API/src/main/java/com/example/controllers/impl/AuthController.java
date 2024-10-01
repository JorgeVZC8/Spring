package com.example.controllers.impl;

import com.example.commons.dtos.TokenResponse;
import com.example.commons.dtos.UserRequest;
import com.example.controllers.AuthApi;
import com.example.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<TokenResponse> createUser(UserRequest userRequest) {
        return ResponseEntity.ok(authService.createUser(userRequest));
    }
    @Override
    public ResponseEntity<TokenResponse> login(UserRequest userRequest) {
        return ResponseEntity.ok(authService.login(userRequest));
    }
}
