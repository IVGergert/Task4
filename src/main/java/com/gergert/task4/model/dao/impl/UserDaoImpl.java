package com.gergert.task4.model.dao.impl;

import com.gergert.task4.model.dao.UserDao;
import com.gergert.task4.model.dao.mapper.impl.UserMapperImpl;
import com.gergert.task4.model.entity.User;
import com.gergert.task4.model.entity.UserRole;
import com.gergert.task4.model.entity.UserStatus;
import com.gergert.task4.model.exception.DaoException;
import com.gergert.task4.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger();
    private final UserMapperImpl mapper = new UserMapperImpl();

    private static final String SQL_FIND_ALL = "SELECT id, email, password, first_name, last_name, role, status, balance FROM users";
    private static final String SQL_FIND_BY_EMAIL = "SELECT id, email, password, first_name, last_name, role, status, balance FROM users WHERE email = ?";
    private static final String SQL_CREATE_USER = "INSERT INTO users (email, password, first_name, last_name, role, status, balance) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String SQL_CHANGE_STATUS_USER = "UPDATE users SET status = ? WHERE id = ?";
    private static final String SQL_CHANGE_ROLE_USER = "UPDATE users SET role = ? WHERE id = ?";

    @Override
    public List<User> findAll() throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        List<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                users.add(mapper.map(rs));
            }
        } catch (SQLException e) {
            logger.error("DAO Error finding all users {}", e);
            throw new DaoException("Error finding all users", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }

        return users;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_EMAIL)) {

            preparedStatement.setString(1, email);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    logger.debug("User with email {} founded", email);
                    return Optional.of(mapper.map(rs));
                } else {
                    logger.debug("User with email {} was not found ", email);
                    return Optional.empty();
                }
            }

        } catch (SQLException e) {
            logger.error("database error finding user by email {}", email ,e);
            throw new DaoException("Database error finding user by email", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public boolean createUser(User user) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_USER)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getRole().name());
            preparedStatement.setString(6, user.getStatus().name());
            preparedStatement.setDouble(7, user.getBalance());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("database error create user email={}", user.getEmail(), e);
            throw new DaoException("Error creating in DB", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public boolean deleteUser(long userId) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER)){
            preparedStatement.setLong(1, userId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Database error deleting user id={}", userId, e);
            throw new DaoException("Error deleting user", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public boolean changeStatusUser(long userId, UserStatus status) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_STATUS_USER)){
            preparedStatement.setString(1, status.name());
            preparedStatement.setLong(2, userId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Database error updating status for user id={}", userId, e);
            throw new DaoException("Error updating user status", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }

    @Override
    public boolean changeRoleUser(long userId, UserRole role) throws DaoException {
        Connection connection = ConnectionPool.getInstance().getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_ROLE_USER)){
            preparedStatement.setString(1, role.name());
            preparedStatement.setLong(2, userId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Database error updating role for user id={}", userId, e);
            throw new DaoException("Error updating user role", e);
        } finally {
            ConnectionPool.getInstance().releaseConnection(connection);
        }
    }


}
