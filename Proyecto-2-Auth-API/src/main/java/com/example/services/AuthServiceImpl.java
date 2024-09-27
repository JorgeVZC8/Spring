package com.example.services;

import com.example.commons.dtos.TokenResponse;
import com.example.commons.dtos.UserRequest;
import com.example.commons.entities.UserModel;
import com.example.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public TokenResponse createUser(UserRequest userRequest) {
        return Optional.of(userRequest)
                .map(this::mapToEntity)
                .map(userRepository::save)
                .map(userCreated-> jwtService.generateToken(userCreated.getId()))
                .orElseThrow(()->new RuntimeException("Error crating user"));
    }

    @Override
    public TokenResponse login(UserRequest userRequest) {
        return userRepository.findByEmail(userRequest.getEmail())
                .filter(userModel -> userModel.getPassword().equals(userRequest.getPassword()))
                .map(userModel -> jwtService.generateToken(userModel.getId()))
                .orElseThrow(()-> new RuntimeException("Error login")) ;
    }

    private UserModel mapToEntity(UserRequest userRequest){
        return UserModel.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .role("USER")
                .build();
    }
}