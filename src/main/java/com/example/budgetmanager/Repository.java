package com.example.budgetmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Repository <T> {
    private static final Logger log = BudgetLogger.getLogger();
    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        Objects.requireNonNull(item, "item must not be null");
        items.add(item);
        log.log(Level.FINE,"Added Item");
    }

    public List<T> findAll() {
        return items;
    }

    public List<T> findWhere(Predicate<T> condition) {
        List<T> result = new ArrayList<>();
        for (T item : items) {
            if (condition.test(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public boolean contains(T item) {
        return items.contains(item);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean remove(T item) {
        log.log(Level.FINE, "Item removed!");
        return items.remove(item);
    }

    public void clear() {
        items.clear();
        log.log(Level.FINE, "Repository cleared.");
    }

}
