package com.lql.inventory.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Handler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> productNotFoundException(ProductNotFoundException exception) {
        exception.printStackTrace();
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> generalException(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.badRequest().body(exception.getClass().toString());
    }
}
