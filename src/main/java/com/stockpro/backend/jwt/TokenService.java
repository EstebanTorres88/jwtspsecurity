package com.stockpro.backend.jwt;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.stockpro.backend.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService implements ITokenService{

    private final TokenProperties tokenProperties;

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenProperties.accessExpiration()))
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    
    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(tokenProperties.secretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }


    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    @Override
    public UUID extractResourceId(String token){
        String resourceId = extractClaim(token, claims -> claims.get(SecurityConstants.CLAIM_RESOURCEID, String.class));
        return UUID.fromString(resourceId);
    }



    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
