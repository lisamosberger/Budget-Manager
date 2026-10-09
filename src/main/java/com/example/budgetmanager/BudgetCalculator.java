package com.example.budgetmanager;

public class BudgetCalculator {

    //ToDO: Write a Test for it!
    public double calculateIncome(Repository<Transaction> repository) {
        double totalIncome = 0;

        for (Transaction transaction : repository.findAll()) {
            if (transaction.type() == TransactionType.INCOME)
                totalIncome += transaction.amount();
        }
        return totalIncome;
    }
    //ToDo: Write a test for it!
    public double calculateExpense(Repository<Transaction> repository) {
        double totalExpense = 0;
        for (Transaction transaction: repository.findAll()){
            if (transaction.type() == TransactionType.EXPENSE)
                totalExpense += transaction.amount();
        }
        return totalExpense;
    }

}
