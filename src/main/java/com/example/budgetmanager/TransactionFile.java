package com.example.budgetmanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionFile {

    public void saveTransactionInFile(List<Transaction> transactions, Path path)
            throws IOException {
        List<String> files = new ArrayList<>();

        for (Transaction transaction: transactions) {
            String file = transaction.date() + ","
                    + transaction.category() + ","
                    + transaction.amount() + ","
                    + transaction.type();
            files.add(file);
        }
        Files.write(path, files);
    }

    public List<Transaction> loadTransactions(Path path) throws IOException {

        List<Transaction> transactions = new ArrayList<>();

        if(!Files.exists(path)){
            return transactions;
        }

        List<String> files = Files.readAllLines(path);

        for (String file : files) {
            try {
                String[] parts = file.split(",",-1);
                if (parts.length != 4) {
                    IO.println("Invalid transaction, line skipped!");
                    continue;
                }
                LocalDate date = LocalDate.parse(parts[0]);
                String category = parts[1];
                double amount = Double.parseDouble(parts[2]);
                TransactionType type = TransactionType.valueOf(parts[3]);

                if (category.isBlank() || amount <= 0 || !Double.isFinite(amount)) {
                    IO.println("Invalid transaction, line skipped!");
                    continue;
                }

                Transaction transaction = new Transaction(date, category, amount, type);
                transactions.add(transaction);

            } catch (IllegalArgumentException e) {
                IO.println("Invalid transaction, line skipped");
            }
        }
         return transactions;
    }

}
