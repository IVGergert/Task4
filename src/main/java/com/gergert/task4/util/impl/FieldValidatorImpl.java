package com.gergert.task4.util.impl;

import com.gergert.task4.util.FieldValidator;

public class FieldValidatorImpl implements FieldValidator {
    public FieldValidatorImpl() {}

    @Override
    public boolean validateLoginInput(String email, String password) {
        if (isNullOrBlank(email) || isNullOrBlank(password)){
            return false;
        }

        return true;
    }

    @Override
    public boolean validateRegistrationInput(String email, String password, String firstName, String lastName) {
        if (isNullOrBlank(email) || isNullOrBlank(password) || isNullOrBlank(firstName) || isNullOrBlank(lastName)){
            return false;
        }

        return true;
    }

    private boolean isNullOrBlank(String field){
        return field == null || field.isBlank();
    }
}
