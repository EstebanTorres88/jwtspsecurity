package com.stockpro.backend.auth;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.constraints.NotEmpty;

public class AuthToken extends AbstractAuthenticationToken {
    private final String token;
    private final UserDetails user;


    public AuthToken(UserDetails user, String token, @NotEmpty Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user = user;
        this.token = token;
        super.setAuthenticated(true);
    }

    @Override
    public @Nullable Object getCredentials() {
        return token;
    }

    @Override
    public @Nullable Object getPrincipal() {
        return user;
    }
    
}
