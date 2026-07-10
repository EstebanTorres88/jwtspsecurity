package com.stockpro.backend.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequestDTO {

    @NotBlank(message = "Email is Required")
    @Email
    private String email;
    
    @NotBlank(message = "Password is Required")
    @Size(min = 8)
    private String password;


    @NotBlank
    private String userName;
}
