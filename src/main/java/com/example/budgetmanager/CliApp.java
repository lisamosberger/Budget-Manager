package com.example.budgetmanager;

import java.time.LocalDate;
import java.util.Locale;

public class CliApp {
    Repository<Transaction> transactions = new Repository<>();
    Repository<String> categories = new Repository<>();

    void main (){
        boolean running = true;
while (running) {
    IO.println("\tBudget Planer" +
            "\n=======================================" +
            "\n1. Add transaction" +
            "\n2. Create category" +
            "\n3. Show all transactions" +
            "\n4. Show balance and summery by category" +
            "\n5. Save to file" +
            "\ne. Exit ");

    String input = IO.readln("Choose an option: ");
    switch (input) {
        case "1" -> addTransaction();
        case "2" -> createCategory();
        case "3" -> showAllTransactions();
        case "4" -> showBalance();
        case "5" -> saveToFile();
        case "e" -> {
            running = false;
            IO.println("Goodbye");
        }
        default -> IO.println("Thats not an option!");
    }
}
    }

    void addTransaction(){
        String input = IO.readln("Income or Expense? ");

        TransactionType type;

        switch (input.toUpperCase()) {
            case "INCOME" -> type =TransactionType.INCOME;
            case "EXPENSE" -> type = TransactionType.EXPENSE;
            default -> {
                IO.println("Thats not an option!");
                return;
            }
        }

        String category = IO.readln("Enter Category: ");
        categoryExists();

        double amount = Double.parseDouble(IO.readln("Enter the amount (SEK): "));

        LocalDate date = LocalDate.now();

        Transaction transaction = new Transaction(date, category, amount, type);
        transactions.add(transaction);
        IO.println("Transaction has been sucessfully added!");
    }

    void createCategory(){

    }

    void showAllTransactions(){

    }

    void showBalance(){

    }

    void saveToFile(){

    }

}
