package com.example.budgetmanager;

import net.bytebuddy.asm.Advice;
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
        String name = "Bread";
        double amount = 50.0;
        TransactionType type = TransactionType.EXPENSE;

        //Act
        Transaction transaction = new Transaction(LocalDate.now(), category, name, amount, type);

        //Assert
        assertEquals(LocalDate.now(), transaction.date());
        assertEquals(category, transaction.category());
        assertEquals(amount, transaction.amount());
        assertEquals(type, transaction.type());

    }

    @Test
    @DisplayName("should throw exception when amount is negative")
    void shouldThrowExceptionWhenAmountIsNegative () {
        LocalDate date = LocalDate.now();

        assertThrows(InvalidTransactionException.class, () -> new Transaction(
                date, "Food", "Bread", -5.0, TransactionType.EXPENSE
        ));
    }

    @Test
    @DisplayName("Should throw for null date")
        void shouldThrowForNullDate () {
            LocalDate date = null;

            assertThrows(InvalidTransactionException.class,
                    () -> new Transaction(date, "Food", "Bread", 5.0, TransactionType.EXPENSE));
        }

}
