package com.stockpro.backend.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.stockpro.backend.exceptions.dtos.ErrorDTO;
import com.stockpro.backend.exceptions.dtos.ValidationErrorDTO;
import com.stockpro.backend.exceptions.userExceptions.EmailAlreadyInUse;
import com.stockpro.backend.exceptions.userExceptions.UserNotFoundException;

public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFound(UserNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(new ErrorDTO(status.value(), ex.getMessage()));
    }

    @ExceptionHandler(EmailAlreadyInUse.class)
    public ResponseEntity<ErrorDTO> handleEmailAlreadyInUse(EmailAlreadyInUse ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body(new ErrorDTO(status.value(), ex.getMessage()));
    }


        @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> handleValidation(MethodArgumentNotValidException ex) {

        Map<String,String> errors = new HashMap<String,String>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(new ValidationErrorDTO(status.value(), "Validation failed", errors));
    }

}
