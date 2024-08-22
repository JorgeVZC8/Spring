package com.example.game_service.controller.impl;

import com.example.game_service.commons.dto.TokenResponse;
import com.example.game_service.commons.dto.UserRequest;
import com.example.game_service.controller.AuthApi;
import com.example.game_service.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<TokenResponse> crateUser(UserRequest userRequest) {
        return ResponseEntity.ok(authService.createUSer(userRequest));
    }
}
