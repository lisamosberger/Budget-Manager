package com.example.budgetmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BudgetCalculatorTest {
    Repository<Transaction> repository = new Repository<>();
    BudgetCalculator calculator = new BudgetCalculator();

    @Test
    @DisplayName("If adding an income total Income should increase.")
    void ifAddingAnIncomeTotalIncomeShouldIncrease(){

        repository.add(new Transaction(LocalDate.of(2026,10,13),
                "Salary", 2000.0, TransactionType.INCOME));
        repository.add(new Transaction(LocalDate.of(2026,10,13),
                "Presents", 300.0, TransactionType.INCOME));
        double result = calculator.calculateIncome(repository);
        assertEquals(2300, result);
    }

    @Test
    @DisplayName("If adding nothing to income shoult return zero")
    void ifAddingNothingToIncomeShouldReturnZero () {
        double result = calculator.calculateIncome(repository);
        assertEquals(0, result);

    }

    @Test
    @DisplayName("If adding something to Expense total expense should Increase")
    void idAddingSomethingToExpenseTotalExpenseShouldIncrease (){
        repository.add(new Transaction (LocalDate.of(2026,10,13),
                "Food",
                1400.0,
                TransactionType.EXPENSE));

        double result = calculator.calculateExpense(repository);
        assertEquals(1400, result);
    }

    @Test
    @DisplayName("If adding nothing to Expenses should return Zero")
    void ifAddingNothingToExpensesShouldReturnZero(){

        double result = calculator.calculateExpense(repository);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Calculating the balance between Expense and Income")
    void IfAddingIncomeAndExpenseShouldGiveDifferenceBetweenThem(){
        repository.add(new Transaction (LocalDate.of(2026,10,13),
                "Food",
                1500.0,
                TransactionType.EXPENSE));
        repository.add((new Transaction(LocalDate.of(2026,10,13),
                "Income",
                1800.0,
                TransactionType.INCOME)));
        double result = calculator.calculateBalance(repository);
        assertEquals(300.0, result);
    }

    @Test
    @DisplayName("If expense > than income should go into negative balance")
    void ifExpenseIsGreaterThanIncomeShouldReturnNegativeNumber(){
        repository.add(new Transaction(LocalDate.of(2026,10,13),
                "Food",
                1500.0,
                TransactionType.EXPENSE));
        repository.add((new Transaction(LocalDate.of(2026,10,13),
                "Salary",
                1200.0,
                TransactionType.INCOME)));
        double result = calculator.calculateBalance(repository);
        assertEquals(-300.0, result);
    }

}