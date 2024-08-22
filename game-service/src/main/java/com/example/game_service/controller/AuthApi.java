package com.example.game_service.controller;

import com.example.game_service.commons.constants.ApiPathVariables;
import com.example.game_service.commons.dto.TokenResponse;
import com.example.game_service.commons.dto.UserRequest;
import com.example.game_service.commons.entities.UserModel;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(ApiPathVariables.v1_ROUTE+ ApiPathVariables.AUTH_ROUTE)
public interface AuthApi {
    @PostMapping("/register")
    ResponseEntity<TokenResponse> crateUser(@RequestBody @Valid UserRequest userRequest);
}
