package com.example.budgetmanager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BudgetCalculatorTest {

    @Test
    @DisplayName("If adding an income total Income should increase.")
    void ifAddingAnIncomeTotalIncomeShouldIncrease(){
        Repository<Transaction> repository = new Repository<>();

        repository.add(new Transaction(LocalDate.of(2026,10,13),
                "Salary", 2000.0, TransactionType.INCOME));
        repository.add(new Transaction(LocalDate.of(2026,10,13),
                "Presents", 300.0, TransactionType.INCOME));
        BudgetCalculator calculator = new BudgetCalculator();
        double result = calculator.calculateIncome(repository);
        assertEquals(2300, result);
    }

    @Test
    @DisplayName("If adding nothing to income shoult return zero")
    void ifAddingNothingToIncomeShouldReturnZero () {
        Repository<Transaction> repository = new Repository<>();
        BudgetCalculator calculator = new BudgetCalculator();
        double result = calculator.calculateIncome(repository);
        assertEquals(0, result);

    }

    @Test
    @DisplayName("If adding something to Expense total expense should Increase")
    void idAddingSomethingToExpenseTotalExpenseShouldIncrease (){
        Repository<Transaction> repository = new Repository<>();
        repository.add(new Transaction (LocalDate.of(2026,10,13),
                "Food",
                1400.0,
                TransactionType.EXPENSE));
        BudgetCalculator calculator = new BudgetCalculator();

        double result = calculator.calculateExpense(repository);
        assertEquals(1400, result);
    }

}