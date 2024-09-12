package com.example.services;

import com.example.commons.dtos.TokenResponse;
import com.example.commons.dtos.UserRequest;

public interface AuthService {
    TokenResponse createUser(UserRequest userRequest);
}
