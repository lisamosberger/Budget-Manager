package com.example.budgetmanager;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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

    TransactionFile transactionfile = new TransactionFile();

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
        transactionfile.saveTransactionInFile(List.of(transaction), file);
        List<String> files = Files.readAllLines(file);
        assertEquals("2026-05-10,Food,1500.0,EXPENSE", files.get(0));

    }

}