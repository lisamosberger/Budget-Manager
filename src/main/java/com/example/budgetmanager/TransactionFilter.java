package com.example.budgetmanager;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class TransactionFilter {

    public static Predicate<Transaction> byType (TransactionType type) {
        return t -> t.type() == type;
    }

    public static Predicate<Transaction> byCategory(String category) {
        return t -> t.category().equalsIgnoreCase(category);
    }

    public static List<Transaction> sortByDate(List<Transaction> transactions) {
        return transactions.stream()
                .sorted(Comparator.comparing(Transaction::date))
                .toList();
    }

    public static List<Transaction> sortByAmount (List<Transaction> transactions) {
        return transactions.stream()
                .sorted(Comparator.comparingDouble(Transaction::amount).reversed())
                .toList();
    }



}
