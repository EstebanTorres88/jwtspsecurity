package com.stockpro.backend.auth;

import com.stockpro.backend.user.User;

public interface IAuthService {

    public User authenticate(String email, String password);
    
}
