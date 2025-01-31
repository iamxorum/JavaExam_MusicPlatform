package com.example.examen.exception;

public class InvalidSubscriptionStateException extends RuntimeException {
    public InvalidSubscriptionStateException(String message) {
        super(message);
    }
}