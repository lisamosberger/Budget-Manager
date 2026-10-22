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
                    + transaction.amount() + ","
                    + transaction.type();
            files.add(file);
        }
        Files.write(path, files);
    }

    public List<Transaction> loadTransactions(Path path) throws IOException, FileFormatException {

        List<Transaction> transactions = new ArrayList<>();

        if(!Files.exists(path)){
            return transactions;
        }

        List<String> files = Files.readAllLines(path);

        for (int i = 0; i < files.size(); i++) {
                String file = files.get(i);
                int lineNumber = i + 1;
try {
                String[] parts = file.split(",");
                if (parts.length != 4) {
                    throw new FileFormatException(lineNumber, "Excpected 4 values");
                }
                LocalDate date = LocalDate.parse(parts[0]);
                String category = parts[1];
                double amount = Double.parseDouble(parts[2]);
                TransactionType type = TransactionType.valueOf(parts[3]);

                if (category.isBlank() || amount <= 0 || !Double.isFinite(amount)) {
                    throw new FileFormatException(lineNumber, "Category is Empty");
                }


                Transaction transaction = new Transaction(date, category, amount, type);
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
