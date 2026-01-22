package com.gergert.task4.model.dao.impl;

import com.gergert.task4.model.entity.User;
import com.gergert.task4.model.entity.UserRole;
import com.gergert.task4.model.entity.UserStatus;
import com.gergert.task4.model.exception.DaoException;
import com.gergert.task4.model.pool.ConnectionPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDaoImplTest {

    private UserDaoImpl userDao;
    private MockedStatic<ConnectionPool> connectionPoolMockedStatic;

    @Mock
    private ConnectionPool connectionPool;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setUp() throws SQLException {
        connectionPoolMockedStatic = mockStatic(ConnectionPool.class);

        connectionPoolMockedStatic.when(ConnectionPool::getInstance).thenReturn(connectionPool);

        when(connectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        userDao = new UserDaoImpl();
    }

    @AfterEach
    void tearDown() {
        connectionPoolMockedStatic.close();
    }

    @Test
    void findAll() throws SQLException, DaoException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, true, false);

        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("email")).thenReturn("u1@test.com");
        when(resultSet.getString("role")).thenReturn("USER");
        when(resultSet.getString("status")).thenReturn("ACTIVE");

        List<User> users = userDao.findAll();

        assertEquals(2, users.size());
        verify(connectionPool).releaseConnection(connection);
    }

    @Test
    void findUserByEmail() throws SQLException, DaoException {
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        when(resultSet.getLong("id")).thenReturn(1L);
        when(resultSet.getString("email")).thenReturn("dao@test.com");
        when(resultSet.getString("password")).thenReturn("pass");
        when(resultSet.getString("first_name")).thenReturn("Name");
        when(resultSet.getString("last_name")).thenReturn("Last");
        when(resultSet.getString("role")).thenReturn("USER");
        when(resultSet.getString("status")).thenReturn("ACTIVE");
        when(resultSet.getDouble("balance")).thenReturn(0.0);

        Optional<User> result = userDao.findUserByEmail("dao@test.com");

        assertTrue(result.isPresent());
        assertEquals("dao@test.com", result.get().getEmail());

        verify(connectionPool).releaseConnection(connection);
    }

    @Test
    void createUser() throws SQLException, DaoException {
        User user = new User();
        user.setEmail("new@test.com");
        user.setPassword("pass");
        user.setFirstName("F");
        user.setLastName("L");
        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);
        user.setBalance(0.0);

        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userDao.createUser(user);

        assertTrue(result);
        verify(preparedStatement).setString(1, "new@test.com");
    }

    @Test
    void deleteUser() throws DaoException, SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userDao.deleteUser(5L);

        assertTrue(result);
        verify(preparedStatement).setLong(1, 5L);
    }

    @Test
    void changeStatusUser() throws SQLException, DaoException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userDao.changeStatusUser(10L, UserStatus.BANNED);

        assertTrue(result);
        verify(preparedStatement).setString(1, "BANNED");
        verify(preparedStatement).setLong(2, 10L);
    }

    @Test
    void changeRoleUser() throws DaoException, SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        boolean result = userDao.changeRoleUser(7L, UserRole.ADMIN);

        assertTrue(result);
        verify(preparedStatement).setString(1, "ADMIN");
        verify(preparedStatement).setLong(2, 7L);
    }
}