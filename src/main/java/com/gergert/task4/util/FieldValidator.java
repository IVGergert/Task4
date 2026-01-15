package com.gergert.task4.util;

public interface FieldValidator {
    boolean validateLoginInput(String email, String password);
    boolean validateRegistrationInput(String email, String password, String firstName, String lastName);
}
