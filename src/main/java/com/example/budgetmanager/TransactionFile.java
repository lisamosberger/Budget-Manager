package com.example.budgetmanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionFile {
    private static final Logger log = Logger.getLogger(TransactionFile.class.getName());

    public void saveTransactionInFile(List<Transaction> transactions, Path path)
            throws IOException {
        List<String> files = new ArrayList<>();

        for (Transaction transaction: transactions) {
            String file = transaction.date() + ","
                    + transaction.category() + ","
                    + transaction.name() + ","
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

        for (int i = 0; i < files.size(); i++) {
                String file = files.get(i);
                int lineNumber = i + 1;
try {
                String[] parts = file.split(",", -1);
                if (parts.length != 5) {
                    throw new FileFormatException(lineNumber, "Expected 5 values");
                }
                LocalDate date = LocalDate.parse(parts[0]);
                String category = parts[1];
                String name = parts[2];
                double amount = Double.parseDouble(parts[3]);
                TransactionType type = TransactionType.valueOf(parts[4]);

                if (category.isBlank() || name.isBlank() || amount <= 0 || !Double.isFinite(amount)) {
                    throw new FileFormatException(lineNumber, "Category is Empty");
                }


                Transaction transaction = new Transaction(date, category, name ,amount, type);
                transactions.add(transaction);

            } catch (FileFormatException e) {
                log.log(Level.WARNING, e.getMessage());
                IO.println("Invalid transaction, line skipped");
            } catch (RuntimeException e) {
                log.log(Level.WARNING, "Invalid transaction format" + e.getMessage());
            }
        }
         return transactions;
    }

}
