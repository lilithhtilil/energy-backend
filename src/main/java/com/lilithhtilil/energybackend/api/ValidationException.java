package com.lilithhtilil.energybackend.api;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
