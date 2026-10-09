package com.example.budgetmanager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    @DisplayName("Creating a Transaction")
    void creatingFirstTransactionInRecord() {
        //Arrange
        String category = "Food";
        double amount = 50.0;
        TransactionType type = TransactionType.EXPENSE;

        //Act
        Transaction transaction = new Transaction(LocalDate.now(), category, amount, type);

        //Assert
        assertEquals(LocalDate.now(), transaction.date());
        assertEquals(category, transaction.category());
        assertEquals(amount, transaction.amount());
        assertEquals(type, transaction.type());


    }

}