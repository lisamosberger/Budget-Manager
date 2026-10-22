package com.example.budgetmanager;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class BudgetLogger {
    private static final Logger logger = Logger.getLogger(BudgetLogger.class.getName());

    public static void init (String Logfile) {
        try {
            FileHandler fileHandler = new FileHandler("application.log", true);

            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            IO.println("Could not logg" + e.getMessage());
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}