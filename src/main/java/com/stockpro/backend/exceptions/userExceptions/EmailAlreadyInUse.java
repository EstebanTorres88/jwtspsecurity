package com.stockpro.backend.exceptions.userExceptions;

public class EmailAlreadyInUse extends RuntimeException {
    
    public EmailAlreadyInUse(String email){
        super("Email already exists " + email);

    }
}
