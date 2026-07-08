package com.stockpro.backend.auth;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stockpro.backend.exceptions.userExceptions.UserNotFoundException;
import com.stockpro.backend.exceptions.userExceptions.UserPasswordException;
import com.stockpro.backend.user.User;
import com.stockpro.backend.user.UserRepository;

@Service
public class AuthService implements IAuthService{

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User authenticate(String email, String password) {
        User user = userRepository.findbyEmail(email).orElseThrow(()-> new UserNotFoundException(email));

        if (!passwordEncoder.matches(password, user.getPassword())) throw new UserPasswordException("Incorrect password");

    
        return user;
    }

    
}
