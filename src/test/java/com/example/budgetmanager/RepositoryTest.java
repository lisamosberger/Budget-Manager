package com.example.budgetmanager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    @Test
    @DisplayName("Add transaction to Repository")
    void shouldaddtransactiontorepository() {
        Repository<Transaction> repository = new Repository<>();

        Transaction transaction = new Transaction(
                LocalDate.now(),
                "Food",
                50.0,
                TransactionType.EXPENSE);

        repository.add(transaction);

        assertEquals(1, repository.findAll().size());
        assertEquals(transaction, repository.findAll().get(0));
    }

}