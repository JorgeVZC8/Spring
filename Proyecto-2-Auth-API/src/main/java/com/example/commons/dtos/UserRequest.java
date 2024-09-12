package com.example.commons.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;

}
