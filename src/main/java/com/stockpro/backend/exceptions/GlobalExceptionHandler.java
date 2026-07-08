package com.stockpro.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.stockpro.backend.exceptions.dtos.ErrorDTO;
import com.stockpro.backend.exceptions.userExceptions.UserNotFoundException;

public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFound(
            UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDTO(
                        404,
                        ex.getMessage()));

    }

}
