package com.example.game_service.service.impl;

import com.example.game_service.commons.dto.TokenResponse;
import com.example.game_service.commons.dto.UserRequest;
import com.example.game_service.commons.entities.UserModel;
import com.example.game_service.repository.UserRepository;
import com.example.game_service.service.AuthService;
import com.example.game_service.service.JwtService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public TokenResponse createUSer(UserRequest userRequest) {
        return Optional.of(userRequest)
                .map(this::mapToEntity)
                .map(userRepository::save)
                .map(userCreated -> jwtService.generateToken(userCreated.getId()))
                .orElseThrow(()-> new RuntimeException("Error creating user"));
    }

    private UserModel mapToEntity(UserRequest userRequest) {
        return UserModel.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .role("USER")
                .build();
    }

}
