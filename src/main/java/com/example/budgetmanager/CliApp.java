package com.example.budgetmanager;

import java.time.LocalDate;
import java.util.Locale;

public class CliApp {
    Repository<Transaction> transactions = new Repository<>();
    Repository<String> categories = new Repository<>();
    BudgetCalculator budgetCalculator = new BudgetCalculator();

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
        IO.println("Choose a Category " +
                "\n=======================================");
        categories.findAll().forEach(c -> IO.println(c));

        String category = IO.readln("Enter Category: ");
        if (!categories.findAll().contains(category)){
            IO.println("This category doesn't exists, please choose another one!");
            return;
        }

        double amount = Double.parseDouble(IO.readln("Enter the amount (SEK): "));

        LocalDate date = LocalDate.now();

        Transaction transaction = new Transaction(date, category, amount, type);
        transactions.add(transaction);
        IO.println("Transaction has been sucessfully added!");
    }

    void createCategory(){
        String category = IO.readln("Enter new category: ");

        if (category.isEmpty()) {
            IO.println("Category can not be empty!");
            return;
        }

        if (categories.findAll().contains(category)) {
            IO.println("Category already exists!");
            return;
        }

        categories.add(category);
        IO.println("Category has succesfully created!");

    }


    void showAllTransactions(){

        IO.println("Every Transaction made " +
                "\n===================================");

        transactions.findAll().forEach(t->IO.println(t));
    }

    void showBalance(){
        IO.println("Your current balance is at "
                + budgetCalculator.calculateBalance(transactions)
                + "\nYour Total Income is at " + budgetCalculator.calculateIncome(transactions)
                + "\nYour Total Expense is at " + budgetCalculator.calculateExpense(transactions));

    }

    void saveToFile(){

    }

}
