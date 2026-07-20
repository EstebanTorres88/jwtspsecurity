package com.stockpro.backend.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockpro.backend.auth.dtos.AuthResponseDTO;
import com.stockpro.backend.auth.dtos.LoginRequestDTO;
import com.stockpro.backend.auth.dtos.RegisterRequestDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> userRegister(@RequestBody @Valid RegisterRequestDTO registerRequestDTO) {
        
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequestDTO));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> userLogin(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }
    
    
}
