package com.stockpro.backend.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record TokenProperties(
    String secretKey, long accessExpiration, long refreshExpiration
)

{}
