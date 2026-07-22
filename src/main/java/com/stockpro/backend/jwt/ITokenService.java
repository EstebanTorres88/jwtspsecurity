package com.stockpro.backend.jwt;

import java.util.Map;
import java.util.UUID;


import org.springframework.security.core.userdetails.UserDetails;

public interface ITokenService {

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    public String extractUsername(String token);
    public UUID extractResourceId(String token);
    public boolean isTokenValid(String token, UserDetails userDetails);
    
}
