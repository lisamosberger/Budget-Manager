package com.example.budgetmanager;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class CliApp {
    private final Path transactionPath = Path.of("transactions.txt");
    private final TransactionFile transactionFile = new TransactionFile();

    Repository<Transaction> transactions = new Repository<>();
    Repository<String> categories = new Repository<>();
    BudgetCalculator budgetCalculator = new BudgetCalculator();

    boolean running = true;
    void main (){

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
        case "e" -> exit();
        default -> IO.println("Thats not an option!");
    }

    if(!running) {
        break;
    }
    IO.readln("Press any key to contrinue...");
}
    }

    void exit () {
        running = false;
        IO.println("Goodbye!");
    }

    void addTransaction(){

        if (categories.findAll().isEmpty()) {
            IO.println("Please create a category first!");
            return;
        }

        String incomeOrExpense = IO.readln("Income or Expense? ");

        TransactionType type;

        switch (incomeOrExpense.toUpperCase()) {
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

        String input = IO.readln("Enter the amount (SEK): ");

        try {
            double amount = Double.parseDouble(
                    input.replace(",", "."));

            Transaction transaction = new Transaction(
                    LocalDate.now(), category, amount, type);
            transactions.add(transaction);
            IO.println("Transaction has been added!");

        } catch (NumberFormatException | InvalidTransactionException e ) {
            IO.println("Invalid Transaction: " + e.getMessage());


        }

    }



    void createCategory(){
        String category = IO.readln("Enter new category: ");

        if (addCategory(category)) {
            IO.println("Added the new category succesfully!");
        } else {
            IO.println("The category can't be empty or already exists!");
        }

    }

    boolean addCategory (String category) {

        category = category.trim();

        if (category.isEmpty()) {
            return false;
        }

        if (categories.findAll().contains(category)) {
            return false;
        }

        categories.add(category);
        return true;
    }

    void showAllTransactions(){

        IO.println("Every Transaction made " +
                "\n===================================");

        transactions.findAll().forEach(t->IO.println(t));
    }

    void showBalance(){
      IO.println("======BUDGET SUMMARY=====" +
              "\nCurrent balance: "
              + budgetCalculator.calculateBalance(transactions)
              + " SEK.");

      IO.println("=====INCOME===== " +
              "\nTotal income: "
              + budgetCalculator.calculateIncome(transactions)
              + " SEK." +
              "\nIncome by category:");
      for (String category: categories.findAll()) {
          double income = budgetCalculator.calculateIncomeByCategory(transactions, category);

          IO.println(category + ":" + income + " SEK.");
      }

      IO.println("=====EXPENSE===== \nTotal expense: " +
              budgetCalculator.calculateExpense(transactions)
              + " SEK." + "" +
              "\nExpense by category:");

      for (String category: categories.findAll()) {
          double expense = budgetCalculator.calculateExpenseByCategory(transactions, category);

          IO.println(category + ": " + expense + " SEK.");

      }

    }

    private void saveToFile(){
        try {
            transactionFile.saveTransactionInFile(transactions.findAll(), transactionPath);
            IO.println("Transaction saved successfully!");
        } catch (IOException e) {
            IO.println("Could not safe transaction! " + e.getMessage());
        }
    }

    private void loadFromFile(){
        try {
            List<Transaction> loadedTransaction = transactionFile.loadTransactions(transactionPath);

            for (Transaction transaction : loadedTransaction) {
                transactions.add(transaction);

                if (!categories.contains(transaction.category())) {
                    categories.add(transaction.category());
                }
            }

            IO.println("Transactions sucessfully reloaded!");
        }catch (IOException e ) {
            IO.println("Transaction could not load!" + e.getMessage());
        }
    }

}


