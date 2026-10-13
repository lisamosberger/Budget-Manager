package com.example.budgetmanager;

import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TransactionFile {

    public void saveTransactionInFile(List<Transaction> transactions, Path path)
            throws IOException {
        List<String> files = new java.util.ArrayList<>();

        for (Transaction transaction: transactions) {
            String file = transaction.date() + ","
                    + transaction.category() + ","
                    + transaction.amount() + ","
                    + transaction.type();
            files.add(file);
        }
        Files.write(path, files);
    }

}
