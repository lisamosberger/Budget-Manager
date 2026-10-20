package com.example.budgetmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CliAppTest {
    //Arrange (Creates for every method a new CliApp, so they dont mess with eachother)
    CliApp app;
    @BeforeEach
    void setUp() {
        app = new CliApp();
    }

    @Test
    @DisplayName("Should add a new Category")
    void shouldAddNewCategory(){
        boolean result = app.addCategory("Food");

        assertTrue(result);
        assertEquals("Food", app.categories.findAll().get(0));
    }

    @Test
    @DisplayName("Should not create same category twice")
    void shouldNotCreateSameCategoryTwice (){
        app.addCategory("Food");
        boolean result = app.addCategory("Food");

        assertFalse(result);
        assertEquals(1, app.categories.findAll().size());
    }

    @Test
    @DisplayName("Should not create an empty category")
    void shouldNotCreateAnEmptyCategory () {
        boolean result = app.addCategory("");

        assertFalse(result);
        assertTrue(app.categories.findAll().isEmpty());
    }

    @Test
    @DisplayName("Should trim spaces from cetegory name")
    void shouldTrimSpacesFromCategoryName() {
        boolean result = app.addCategory(" Food   ");

        assertTrue(result);
        assertEquals( "Food",app.categories.findAll().get(0));
    }

    @Test
    @DisplayName("Should not add category that only contains spaces")
    void shouldNotAddCategoryThatOnlyContainsSpaces() {
        boolean result = app.addCategory("   ");

        assertFalse(result);
        assertTrue(app.categories.findAll().isEmpty());
    }

    @Test
    @DisplayName("Should store a transaction in the repository")
    void shouldStoreATransactionInTheRepository() {
        Transaction transaction = new Transaction(LocalDate.now(),
                "Food",
                50.0,
                TransactionType.EXPENSE);

        app.transactions.add(transaction);
        //app.addtransaction(); goes wrong because no category exists.
        assertEquals(1, app.transactions.findAll().size());
        assertEquals(transaction, app.transactions.findAll().get(0));

    }

    @Test
    @DisplayName("Should not store categories twice")
    void shouldNotStoreCategoriesTwice() {
        boolean firstCategory = app.addCategory("Food");
        boolean secondCategory = app.addCategory("Food");

        assertTrue(firstCategory);
        assertFalse(secondCategory);
        assertEquals(1, app.categories.findAll().size());
    }

}