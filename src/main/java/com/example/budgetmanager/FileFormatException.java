package com.example.budgetmanager;

public class FileFormatException extends Exception{
    private final int lineNumber;

    public FileFormatException(int lineNumber, String message) {
        super("Line: " + lineNumber + ": " + message);
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
