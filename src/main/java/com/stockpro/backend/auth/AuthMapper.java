package com.stockpro.backend.auth;

import org.mapstruct.Mapper;

import com.stockpro.backend.auth.dtos.AuthResponseDTO;
import com.stockpro.backend.user.User;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    AuthResponseDTO toAuthResponse(User user, String token);
    
}
