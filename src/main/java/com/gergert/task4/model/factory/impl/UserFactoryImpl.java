package com.gergert.task4.model.factory.impl;

import com.gergert.task4.model.entity.User;
import com.gergert.task4.model.entity.UserRole;
import com.gergert.task4.model.entity.UserStatus;
import com.gergert.task4.model.factory.UserFactory;

public class UserFactoryImpl implements UserFactory {
    @Override
    public User createUser(String email, String password, String firstName, String lastName) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);
        user.setBalance(0.0);

        return user;
    }

    @Override
    public User createAdmin(String email, String password, String firstName, String lastName) {
        User admin = new User();
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setRole(UserRole.ADMIN);
        admin.setStatus(UserStatus.ACTIVE);
        admin.setBalance(0.0);
        return admin;
    }
}
