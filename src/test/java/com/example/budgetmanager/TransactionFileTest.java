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
                "Cucumber",
                1500.0,
                TransactionType.EXPENSE);

        Path file = tempdir.resolve("transaction.txt");
        transactionFile.saveTransactionInFile(List.of(transaction), file);
        List<String> files = Files.readAllLines(file);
        assertEquals("2026-05-10,Food,Cucumber,1500.0,EXPENSE", files.get(0));

    }

    @Test
    @DisplayName("Should save multiple transactions in seperate rows")
    void shouldSaveMultipleTransactionsInSeperateRows () throws IOException {
        Transaction firstTransaction = new Transaction(LocalDate.of(2026,5,7)
                , "Food",
                "Ice cream"
                , 15.0, TransactionType.EXPENSE);
        Transaction secondTransaction = new Transaction(LocalDate.of(2026,4,2)
                , "Food",
                "Bread"
                , 14.0,
                TransactionType.EXPENSE);

        Path file = tempdir.resolve("transaction.txt");

        transactionFile.saveTransactionInFile(List.of(firstTransaction, secondTransaction),
                file);

        List<String> files = Files.readAllLines(file);

        assertEquals(2, files.size());
        assertEquals("2026-05-07,Food,Ice cream,15.0,EXPENSE", files.get(0));
        assertEquals("2026-04-02,Food,Bread,14.0,EXPENSE", files.get(1));
    }

    @Test
    @DisplayName("Should skip broken lines")
    void shouldSkipBrokenLines() throws IOException {
        Path file = tempdir.resolve("broken.txt");
        Files.write(file, List.of(
            "2023-05-06,Food,Bread,15.0,EXPENSE",
            "this is broken",
                "not-a-date,Salary,Company,200,EXPENSE",
            "2023-01-05,Salary,Company,100.0,INCOME"
        ));
        List<Transaction> loaded =transactionFile.loadTransactions(file);
        assertEquals(2, loaded.size());
        assertEquals("Food", loaded.get(0).category());
        assertEquals("Salary", loaded.get(1).category());

    }

    @Test
    @DisplayName("Should give empty list")
    void shouldGiveEmptyList() throws IOException {
        Path file = Files.createFile(tempdir.resolve("empty.txt"));

        assertTrue(transactionFile.loadTransactions(file).isEmpty());
    }

    @Test
    @DisplayName("Should Throw FileFormatException for wrong field count")
    void shouldThrowFileFormatExceptionForWrongFieldCount() throws IOException {
        FileFormatException e = new FileFormatException(7, "Expected 4 values");

        assertEquals(7, e.getLineNumber());
        assertEquals("Line: 7: Expected 4 values",
                e.getMessage());
    }

    @Test
    @DisplayName("Save and load should give the same transaction")
    void saveAndLoadShouldGiveTheSameTransaction () throws IOException {
        List<Transaction> save = List.of(new Transaction(LocalDate.now(), "Food",
                        "Bread",
                        15.0,
                        TransactionType.EXPENSE),
                new Transaction(LocalDate.now(), "Salary",
                        "Company",
                        20.0,
                        TransactionType.INCOME));
Path file = tempdir.resolve("beforeAndAfter.txt");

transactionFile.saveTransactionInFile(save, file);
List<Transaction>loaded =transactionFile.loadTransactions(file);

assertEquals(save, loaded);
    }

}