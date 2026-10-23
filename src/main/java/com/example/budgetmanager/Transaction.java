package com.example.budgetmanager;

import java.time.LocalDate;

public record Transaction(LocalDate date,
                          String category,
                          String name,
                          double amount,
                          TransactionType type) {

    public Transaction {
        if (date == null) {
            throw new InvalidTransactionException("Date cannot be null");
        }
        if (category == null||category.isBlank()) {
            throw new InvalidTransactionException("Category cant be empty");
        }

        if (name == null || name.isBlank()) {
            throw new InvalidTransactionException("Name cant be empty");
        }

        if (category.contains(",")){
            throw new InvalidTransactionException("Category can't contain commas");
        }
        if (amount <= 0 || !Double.isFinite(amount)){
            throw new InvalidTransactionException("The Amount can't be smaller than 0!");
        }
        if (type == null) {
            throw new InvalidTransactionException("Type can't be null!");
        }
    }
}
