package com.stockpro.backend.auth;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stockpro.backend.auth.dtos.AuthResponseDTO;
import com.stockpro.backend.auth.dtos.LoginRequestDTO;
import com.stockpro.backend.auth.dtos.RegisterRequestDTO;
import com.stockpro.backend.exceptions.userExceptions.EmailAlreadyInUse;
import com.stockpro.backend.jwt.TokenService;
import com.stockpro.backend.user.User;
import com.stockpro.backend.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthMapper authMapper;

    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password()));
        User user = (User) authentication.getPrincipal();

        String token = tokenService.generateToken(buildExtraClaims(user), user);

        return authMapper.toAuthResponse(user, token);

    }

    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO) {

        if (userRepository.existsByEmail(registerRequestDTO.email())) {
            throw new EmailAlreadyInUse(registerRequestDTO.email());

        }

        User user = User.builder()
                .email(registerRequestDTO.email())
                .password(passwordEncoder.encode(registerRequestDTO.password()))
                .createdAt(LocalDate.now())
                .resourceId(UUID.randomUUID())
                .build();

        userRepository.save(user);

        String token = tokenService.generateToken(buildExtraClaims(user), user);
        return authMapper.toAuthResponse(user, token);

    }

    private Map<String, Object> buildExtraClaims(User user) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("resourceId", user.getResourceId());
        return claims;
    }
}
