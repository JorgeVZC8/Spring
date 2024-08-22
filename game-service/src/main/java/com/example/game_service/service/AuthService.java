package com.example.game_service.service;

import com.example.game_service.commons.dto.TokenResponse;
import com.example.game_service.commons.dto.UserRequest;
import org.springframework.stereotype.Service;


public interface AuthService {
    TokenResponse createUSer(UserRequest userRequest);
}
