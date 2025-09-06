package com.registration.practice.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// yung mga @notblank @pattern etc etc... aside sa pag lagay ng @Valid
// need rin na gumawa ng ganito para ma send sila sa response
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // Handles validation errors that happen when saving entities to the database (e.g., JPA/Hibernate validation).
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        // Return the first validation error message
        String message = ex.getConstraintViolations().iterator().next().getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    // Handles validation errors for request bodies in your controller methods (e.g., when a user submits a form with missing or invalid fields).
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        // Get the first field error message
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String message = (fieldError != null) ? fieldError.getDefaultMessage() : "Invalid input";
        return ResponseEntity.badRequest().body(message);
    }
}
