package com.example.controllers;

import com.example.commons.constants.ApiPathConstants;
import com.example.commons.dtos.TokenResponse;
import com.example.commons.dtos.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(ApiPathConstants.V1_ROUTE+ApiPathConstants.AUTH_ROUTE)
public interface AuthApi {
    @PostMapping(value = "/register")
    ResponseEntity<TokenResponse> createUser(@RequestBody @Valid UserRequest userRequest);

    @PostMapping(value = "/login")
    ResponseEntity<TokenResponse> login(@RequestBody @Valid UserRequest userRequest);
}
