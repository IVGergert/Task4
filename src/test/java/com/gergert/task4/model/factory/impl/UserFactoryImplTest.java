package com.gergert.task4.model.factory.impl;

import com.gergert.task4.model.entity.User;
import com.gergert.task4.model.entity.UserRole;
import com.gergert.task4.model.entity.UserStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryImplTest {

    private final UserFactoryImpl factory = new UserFactoryImpl();

    @Test
    void createUser() {
        User user = factory.createUser("test@mail.com", "hash", "Ivan", "Ivanov");

        assertNotNull(user);
        assertEquals("test@mail.com", user.getEmail());
        assertEquals(UserRole.USER, user.getRole());
        assertEquals(UserStatus.ACTIVE, user.getStatus());
        assertEquals(0.0, user.getBalance());
    }

    @Test
    void createAdmin() {
        User admin = factory.createAdmin("admin@mail.com", "hash", "Admin", "User");

        assertEquals(UserRole.ADMIN, admin.getRole());
    }
}