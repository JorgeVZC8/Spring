package com.example.commons.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
}
