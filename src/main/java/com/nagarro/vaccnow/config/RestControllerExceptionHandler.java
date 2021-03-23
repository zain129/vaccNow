package com.nagarro.vaccnow.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity mainExceptionHandler(Exception ex, WebRequest request) {
        Map result = new HashMap<>();
        result.put("message", ex.getLocalizedMessage());
        result.put("status", HttpStatus.NOT_FOUND.value());
        result.put("data", null);
        return ResponseEntity.ok(result);
    }

}
