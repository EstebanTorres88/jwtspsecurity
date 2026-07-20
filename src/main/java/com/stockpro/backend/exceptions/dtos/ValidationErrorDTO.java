package com.stockpro.backend.exceptions.dtos;

import java.util.Map;

public record ValidationErrorDTO(int status, String message, Map<String,String> errors) {
    
}
