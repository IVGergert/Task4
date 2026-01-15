package com.gergert.task4.model.factory;

import com.gergert.task4.model.entity.User;

public interface UserFactory {
    User createUser(String email, String password, String firstName, String lastName);
    User createAdmin(String email, String password, String firstName, String lastName);
}
