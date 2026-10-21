package com.example.budgetmanager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionFileTest {

    TransactionFile transactionFile = new TransactionFile();

    @TempDir
    Path tempdir;

    @Test
    @DisplayName("If adding first transaction, should save in row 0")
    void ifAddingFirstTransactionShouldSaveInRowZero () throws IOException {
        Transaction transaction = new Transaction(LocalDate.of(2026,05,10),
                "Food",
                1500.0,
                TransactionType.EXPENSE);

        Path file = tempdir.resolve("transaction.txt");
        transactionFile.saveTransactionInFile(List.of(transaction), file);
        List<String> files = Files.readAllLines(file);
        assertEquals("2026-05-10,Food,1500.0,EXPENSE", files.get(0));

    }

    @Test
    @DisplayName("Should save multiple transactions in seperate rows")
    void shouldSaveMultipleTransactionsInSeperateRows () throws IOException {
        Transaction firstTransaction = new Transaction(LocalDate.of(2026,5,7)
                , "Food"
                , 15.0, TransactionType.EXPENSE);
        Transaction secondTransaction = new Transaction(LocalDate.of(2026,4,2)
                , "Food"
                , 14.0,
                TransactionType.EXPENSE);

        Path file = tempdir.resolve("transaction.txt");

        transactionFile.saveTransactionInFile(List.of(firstTransaction, secondTransaction),
                file);

        List<String> files = Files.readAllLines(file);

        assertEquals(2, files.size());
        assertEquals("2026-05-07,Food,15.0,EXPENSE", files.get(0));
        assertEquals("2026-04-02,Food,14.0,EXPENSE", files.get(1));
    }


}