package com.example.budgetmanager;

import org.junit.jupiter.api.BeforeEach;
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
            "Bread",
            50.0,
            TransactionType.EXPENSE);

    Transaction transaction2 = new Transaction(
            LocalDate.now(),
            "Transport",
            "Car",
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

    @Test
    @DisplayName("Should work with other types too")
    void shouldWorkWithOtherTypesToo() {
        Repository<String> names = new Repository<>();
        names.add("Food");
        names.add("Cool");

        assertEquals(List.of("Food"), names.findWhere(s -> s.startsWith("F")));
    }

    @Test
    @DisplayName("Should remove existing item")
    void shouldRemoveExistingItem () {
        repository.add(transaction);

        boolean result = repository.remove(transaction);

        assertTrue(result);
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    @DisplayName("Should not remove transaction that does not exist")
    void shouldNotRemoveNonExistingTransaction() {
        repository.add(transaction);

        boolean result = repository.remove(transaction2);

        assertFalse(result);
        assertEquals(1, repository.findAll().size());
    }

}