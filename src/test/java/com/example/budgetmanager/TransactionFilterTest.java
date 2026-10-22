package com.example.budgetmanager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionFilterTest {
    Repository<Transaction> repository = new Repository<>();


    @Test
    @DisplayName("Should filter by type")
    void shouldFilterByType() {
        Transaction income = new Transaction(LocalDate.now(), "Salary", 15.0, TransactionType.INCOME);
        Transaction expense = new Transaction(LocalDate.now(), "Food", 15.0, TransactionType.EXPENSE);

        repository.add(income);
        repository.add(expense);

        List<Transaction> result = repository.findWhere(TransactionFilter.byType(TransactionType.EXPENSE));

        assertEquals(List.of(expense),result);

    }

    @Test
    @DisplayName("Sort by amount")
    void shouldSortByAmount () {
        Transaction low = new Transaction(LocalDate.now(), "Food", 1.0, TransactionType.EXPENSE);
        Transaction highest = new Transaction(LocalDate.now(), "Animal", 15.0, TransactionType.EXPENSE);
        Transaction middle = new Transaction(LocalDate.now(), "Salary", 5.0, TransactionType.EXPENSE);

        List<Transaction> result = TransactionFilter.sortByAmount(List.of(low, highest, middle));

        assertEquals(List.of(highest, middle, low), result);

    }

    @Test
    @DisplayName("Sorting should not change the order in repository")
    void sortingShouldNotChangeTheOrderInRepository() {
        Transaction first = new Transaction(LocalDate.now(), "Food", 1.0, TransactionType.EXPENSE);
        Transaction second = new Transaction(LocalDate.now(), "Animal", 15.0, TransactionType.EXPENSE);
        Transaction third = new Transaction(LocalDate.now(), "Salary", 5.0, TransactionType.EXPENSE);

        repository.add(first);
        repository.add(second);
        repository.add(third);

        TransactionFilter.sortByAmount(repository.findAll());

        assertEquals(first, repository.findAll().get(0));

    }







}