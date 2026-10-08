package com.infy.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MedicineException {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> invalidArgumentException(IllegalArgumentException ex){
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }
}
