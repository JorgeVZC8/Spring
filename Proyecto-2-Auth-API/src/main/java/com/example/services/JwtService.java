package com.example.services;

import com.example.commons.dtos.TokenResponse;
import io.jsonwebtoken.Claims;
import org.antlr.v4.runtime.Token;

public interface JwtService {
    TokenResponse generateToken( Long userId);
    Claims getClaims(String token);
    boolean isExpired(String token);
    Integer extractUserId(String token);
}
