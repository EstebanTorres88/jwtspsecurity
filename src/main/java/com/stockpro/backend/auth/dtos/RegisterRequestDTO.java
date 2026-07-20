package com.stockpro.backend.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(

        @NotBlank(message = "Email is Required") @Email String email,

        @NotBlank(message = "Password is Required") @Size(min = 8, message = "Password must have minimum 8 characters") String password

) {}
