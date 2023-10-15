package com.currency.currency_module;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.currency.currency_module.ResourceNotFound.ResourceNotFound;






@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public  ResponseEntity<Object>  resourcenotfoundhandling(ResourceNotFound ex){
                // Create a custom error response with a meaningful message
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Resource Not Found");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        errorResponse.put("timestamp", new Date());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
