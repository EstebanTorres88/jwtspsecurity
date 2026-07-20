package com.stockpro.backend.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(

        @NotBlank(message = "A email is required") @Email String email,

        @NotBlank(message = "A password is required")@Size(min = 8, message = "Password must have minimum 8 characters") String password

) {
}
