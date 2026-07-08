package com.stockpro.backend.exceptions.userExceptions;

public class UserPasswordException extends RuntimeException {
    
    public UserPasswordException(String message){
        super(message);
    }
}
