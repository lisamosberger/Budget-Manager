package com.example.budgetmanager;

import java.awt.dnd.DropTarget;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
TODO: Another format in Show all transactions!,
 create test for everything,
 ReadME
 Reflect all Errors and test handling i had
 Try to find more errors and go trough everything in the document for G and VG grade
 */
public class CliApp {
    private static final Logger log = BudgetLogger.getLogger();
    private final Path transactionPath = Path.of("transactions.txt");
    private final TransactionFile transactionFile = new TransactionFile();

    Repository<Transaction> transactions = new Repository<>();
    Repository<String> categories = new Repository<>();
    BudgetCalculator budgetCalculator = new BudgetCalculator();

    boolean running = true;
    void main (){
        BudgetLogger.init("BudgetPlaner.log");
        log.info("Application started");
        loadFromFile();

while (running) {
    IO.println("\tBudget Planer" +
            "\n=======================================" +
            "\n1.  Add transaction" +
            "\n2. Create category" +
            "\n3. Show all transactions" +
            "\n4. Show balance and summery by category" +
            "\n5. Save to file" +
            "\n6. Filter transactions" +
            "\ne. Exit ");

    String input = IO.readln("Choose an option: ");
    switch (input) {
        case "1" -> addTransaction();
        case "2" -> createCategory();
        case "3" -> showAllTransactions();
        case "4" -> showBalance();
        case "5" -> saveToFile();
        case "6" -> filterTransactions();
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
        saveToFile();
        running = false;
        log.info("Application stopped.");
        IO.println("Goodbye!");
    }



    void addTransaction(){

        if (categories.findAll().isEmpty()) {
            log.warning("Tried adding transaction without any category!");
            IO.println("Please create a category first!");
            return;
        }

        String incomeOrExpense = IO.readln("Income or Expense? ");

        TransactionType type;

        switch (incomeOrExpense.toUpperCase()) {
            case "INCOME" -> type =TransactionType.INCOME;
            case "EXPENSE" -> type = TransactionType.EXPENSE;
            default -> {
                log.log(Level.WARNING, "Invalid transactionType ", incomeOrExpense);
                IO.println("Thats not an option!");
                return;
            }
        }
        IO.println("Choose a Category " +
                "\n=======================================");
        categories.findAll().forEach(IO::println);

        String categoryInput = IO.readln("Enter Category: ").trim();

        Optional<String> category = findCategory(categoryInput);
            if (category.isEmpty()) {
                log.log(Level.WARNING, "Unknown category: " + categoryInput);
                IO.println("This category does not exist!");
                return;
            }

        String input = IO.readln("Enter the amount (SEK): ");

        try {
            double amount = Double.parseDouble(
                    input.replace(",", "."));

            Transaction transaction = new Transaction(
                    LocalDate.now(), category.get(), amount, type);
            transactions.add(transaction);
            log.log(Level.INFO, "Transaction has been added", transaction);
            IO.println("Transaction has been added!");

        } catch (NumberFormatException | InvalidTransactionException e ) {
            log.log(Level.WARNING, "Amount is not a number or has been rejected.");
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

        category = formatCategory(category);

        if (category.isEmpty()|| category.contains(",")) {
            log.log(Level.WARNING, "Rejected category name!");
            return false;
        }

        if (categories.findAll().contains(category)) {
            log.log(Level.WARNING, "Category already exists!");
            return false;
        }

        categories.add(category);
        log.log(Level.INFO, "Category created!");
        return true;
    }

    Optional<String> findCategory(String category) {
    return categories.findAll().stream().filter(c -> c.equalsIgnoreCase(category.trim()))
        .findFirst();
    }

    void showAllTransactions(){
        IO.println("Every Transaction made " +
                "\n===================================");
        printTransactions(TransactionFilter.sortByDate(transactions.findAll()));

    }

    private void printTransactions(List<Transaction> list) {
        if (list.isEmpty()) {
            IO.println("No transaction found");
            return;
        }

        list.forEach(t->IO.println(
                "Date: " + t.date() +
                        "\nCategory: " + t.category()
                        + "\nAmount:" + t.amount() + " " +
                        t.type()
                        + "\n----------------------------------" ));
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
      budgetCalculator.sumByCategory(transactions, TransactionType.INCOME)
              .forEach((category, sum) -> IO.println(category + ": " + sum + "SEK"));

      IO.println("=====EXPENSE===== \nTotal expense: " +
              budgetCalculator.calculateExpense(transactions)
              + " SEK." + "" +
              "\nExpense by category:");
      budgetCalculator.sumByCategory(transactions, TransactionType.EXPENSE)
              .forEach((category, sum) -> IO.println(category + ": " + sum + "SEK"));

    }

    private void filterTransactions () {
        Predicate<Transaction> filter= t ->true;

        String input =IO.readln("Type(Income/Expense), enter for all: ").trim();
        if (!input.isEmpty()) {
            try {
                filter = filter.and(TransactionFilter.byType(TransactionType.valueOf(input.toUpperCase())));
            } catch (IllegalArgumentException e) {
                log.log(Level.WARNING, "Couldn't Filter");
                IO.println("Thats not a possible answer");
                return;
            }
        }

        List<Transaction> result =transactions.findWhere(filter);
        result = TransactionFilter.sortByAmount(result);
        printTransactions(result);

    }

    private void saveToFile(){
        try {
            transactionFile.saveTransactionInFile(transactions.findAll(), transactionPath);
            log.info("File sucessfully saved!");
            IO.println("Transaction saved successfully!");
        } catch (IOException e) {
            log.log(Level.WARNING, "File had problems saving!");
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
            IO.println("Loaded Transactions: " + loadedTransaction.size());
            IO.println("Transactions sucessfully reloaded!");
            log.info("File loaded sucessfully!");
        }catch (IOException e ) {
            log.log(Level.WARNING, "File could not load!");
            IO.println("Transaction could not load!" + e.getMessage());
        }

    }

    private String formatCategory(String category) {
        category = category.trim().toLowerCase();

        if (category.isEmpty()) {
            return category;
        }
        return category.substring(0, 1). toUpperCase()
                + category.substring(1);
    }

}


