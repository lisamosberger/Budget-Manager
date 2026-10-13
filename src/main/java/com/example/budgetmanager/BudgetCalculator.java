package com.example.budgetmanager;

public class BudgetCalculator {


    public double calculateIncome(Repository<Transaction> repository) {
        double totalIncome = 0;

        for (Transaction transaction : repository.findAll()) {
            if (transaction.type() == TransactionType.INCOME)
                totalIncome += transaction.amount();
        }
        return totalIncome;
    }

    public double calculateExpense(Repository<Transaction> repository) {
        double totalExpense = 0;
        for (Transaction transaction: repository.findAll()){
            if (transaction.type() == TransactionType.EXPENSE)
                totalExpense += transaction.amount();
        }
        return totalExpense;
    }

    public double calculateBalance(Repository<Transaction> repository) {
        double amount = calculateIncome(repository) - calculateExpense(repository);
        return amount;
    }
}
