package com.gergert.task4.model.dao.mapper.impl;

import com.gergert.task4.model.entity.User;
import com.gergert.task4.model.entity.UserRole;
import com.gergert.task4.model.entity.UserStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserMapperImplTest {

    @Mock
    private ResultSet resultSet;

    private AutoCloseable closeable;

    private final UserMapperImpl mapper = new UserMapperImpl();

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void map() throws SQLException {
        when(resultSet.getLong("id")).thenReturn(10L);
        when(resultSet.getString("email")).thenReturn("mapper@test.com");
        when(resultSet.getString("password")).thenReturn("pass123");
        when(resultSet.getString("first_name")).thenReturn("Alex");
        when(resultSet.getString("last_name")).thenReturn("Gergert");
        when(resultSet.getString("role")).thenReturn("ADMIN");
        when(resultSet.getString("status")).thenReturn("BANNED");
        when(resultSet.getDouble("balance")).thenReturn(100.50);

        User user = mapper.map(resultSet);

        assertEquals(10L, user.getId());
        assertEquals("mapper@test.com", user.getEmail());
        assertEquals(UserRole.ADMIN, user.getRole());
        assertEquals(UserStatus.BANNED, user.getStatus());
        assertEquals(100.50, user.getBalance());
    }
}