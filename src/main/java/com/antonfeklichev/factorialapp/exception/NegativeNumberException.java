package com.antonfeklichev.factorialapp.exception;

public class NegativeNumberException extends RuntimeException {
    public NegativeNumberException(String msg) {
        super(msg);
    }
}
