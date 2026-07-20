package com.stockpro.backend.exceptions.userExceptions;

public class UserNotFoundException extends RuntimeException {

   
    public UserNotFoundException(String email) {
        super("User not found with email: " + email);
    }

}
