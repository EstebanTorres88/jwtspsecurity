package com.stockpro.backend.exceptions.dtos;

public class ErrorDTO {
    int status;
    String message;

    public ErrorDTO(int status, String message) {
        this.status = status;
        this.message = message;
    }
}