package com.example.examen.exception;

public class DuplicateSubscriptionException extends SubscriptionException {
    public DuplicateSubscriptionException(String message) {
        super(message);
    }
}