package com.stockpro.backend.exceptions.userExceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(String email) {
        super("User not found with id: " + email);
    }

}
