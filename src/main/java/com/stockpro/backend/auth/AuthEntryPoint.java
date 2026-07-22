package com.stockpro.backend.auth;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.stockpro.backend.exceptions.dtos.ErrorDTO;
import com.stockpro.backend.security.SecurityConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class AuthEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String detail = (String) request.getAttribute(SecurityConstants.JWT_EXCEPTION);
        String errorMessage = detail != null ? detail : "JWT is missing";
        
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ErrorDTO errorDTO = new ErrorDTO(status.value(), errorMessage);

        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(errorDTO));
    
    }
}
