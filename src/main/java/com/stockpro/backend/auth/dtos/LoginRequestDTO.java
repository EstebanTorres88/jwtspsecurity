package com.stockpro.backend.auth.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginRequestDTO {

    @NotBlank(message = "A email is required")
    private String email;

    @NotNull(message = "A password is required")
    @NotBlank
    private String password;
    
}
