package com.antonfeklichev.factorialapp.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class FactorialExceptionHandler {

    record ErrorResponse(String msg) {
    }

    @ExceptionHandler
    ResponseEntity<ErrorResponse> handleNegativeNumberException(NegativeNumberException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

}
