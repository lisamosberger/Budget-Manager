package com.example.budgetmanager;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class BudgetCalculator {


    public double calculateIncome(Repository<Transaction> repository) {
       return repository.findAll().stream()
               .filter(t -> t.type() == TransactionType.INCOME)
               .mapToDouble(Transaction::amount)
               .sum();
    }

    public double calculateExpense(Repository<Transaction> repository) {
       return repository.findAll().stream()
               .filter(t -> t.type() == TransactionType.EXPENSE)
               .mapToDouble(Transaction::amount)
               .sum();
    }

    public double calculateByCategory(Repository<Transaction> repository, TransactionType type, String category) {
        return repository.findAll().stream()
                .filter(t->t.type() == type)
                .filter(t -> t.category().equalsIgnoreCase(category))
                .mapToDouble(Transaction::amount)
                .sum();
    }

    public Map<String, Double> sumByCategory(Repository<Transaction> repository, TransactionType type) {
        return repository.findAll().stream()
                .filter(t-> t.type() == type)
                .collect(Collectors.groupingBy(
                        Transaction::category,
                        TreeMap::new,
                        Collectors.summingDouble(Transaction::amount)
                ));
    }

    public double calculateBalance(Repository<Transaction> repository) {
        double amount = calculateIncome(repository) - calculateExpense(repository);
        return amount;
    }
}
