package com.example.budgetmanager;

public class InvalidTransactionException extends RuntimeException{
    public InvalidTransactionException (String message) {
        super(message);
    }
}
