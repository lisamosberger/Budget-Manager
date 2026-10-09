package com.example.budgetmanager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {
    Repository<Transaction> repository = new Repository<>();
    Transaction transaction = new Transaction(
            LocalDate.now(),
            "Food",
            50.0,
            TransactionType.EXPENSE);

    Transaction transaction2 = new Transaction(
            LocalDate.now(),
            "Transport",
            25.0,
            TransactionType.EXPENSE
    );

    @Test
    @DisplayName("Add transaction to Repository")
    void shouldaddtransactiontorepository() {

        repository.add(transaction);

        assertEquals(1, repository.findAll().size());
        assertEquals(transaction, repository.findAll().get(0));
    }

    @Test
    @DisplayName("Find Transaction by category")
    void findTransactionByCategory(){
        repository.add(transaction);
        repository.add(transaction2);

        List<Transaction> result = repository.findWhere(
                transaction -> transaction.category().equals("Food")
        );

        assertEquals(1, result.size());
        assertEquals(transaction, result.get(0));
    }

}