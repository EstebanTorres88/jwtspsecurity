package com.stockpro.backend.auth.dtos;

import java.util.UUID;

public record AuthResponseDTO(
    String token,
    UUID resourceId,
    String email,
    String role
    
){}
