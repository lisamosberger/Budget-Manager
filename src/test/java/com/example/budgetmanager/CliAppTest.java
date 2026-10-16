package com.example.budgetmanager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CliAppTest {
    //Arrange
    CliApp app = new CliApp();

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

}