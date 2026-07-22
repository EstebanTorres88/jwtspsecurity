package com.stockpro.backend.auth;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.stockpro.backend.jwt.TokenService;
import com.stockpro.backend.security.SecurityConstants;
import com.stockpro.backend.user.UserService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthFIlter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserService userService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String publicEndpoint = "/auth";
        String path = request.getServletPath();
        return path.startsWith(publicEndpoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith(SecurityConstants.BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            token = authHeader.substring(SecurityConstants.BEARER_PREFIX.length());
            userEmail = tokenService.extractUsername(token);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails user = userService.loadUserByUsername(userEmail);

                if (tokenService.isTokenValid(token, user)) {
                    AuthToken authToken = new AuthToken(user, token, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }

        } catch (JwtException ex) {
            SecurityContextHolder.clearContext();
            request.setAttribute(SecurityConstants.JWT_EXCEPTION, ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
