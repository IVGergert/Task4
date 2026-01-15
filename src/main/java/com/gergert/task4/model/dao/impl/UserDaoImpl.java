package com.gergert.task4.model.dao.impl;

import com.gergert.task4.model.dao.UserDao;
import com.gergert.task4.model.dao.mapper.impl.UserMapperImpl;
import com.gergert.task4.model.entity.User;
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
    private static final String SQL_FIND_BY_ID = "SELECT id, email, password, first_name, last_name, role, status, balance FROM users WHERE id = ?";
    private static final String SQL_FIND_BY_EMAIL = "SELECT id, email, password, first_name, last_name, role, status, balance FROM users WHERE email = ?";
    private static final String SQL_CREATE_USER = "INSERT INTO users (email, password, first_name, last_name, role, status, balance) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_CHECK_EMAIL_EXISTS = "SELECT 1 FROM users WHERE email = ?";

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                users.add(mapper.map(rs));
            }
        } catch (SQLException e) {
            logger.error("Error finding all users {}", e);
            throw new DaoException("Error finding all users", e);
        }

        return users;
    }

    @Override
    public Optional<User> findUserById(long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID)) {

            preparedStatement.setLong(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()){
                if (rs.next()) {
                    return Optional.of(mapper.map(rs));
                }
            }

        } catch (SQLException e) {
            logger.error("Error finding user by id {}", id, e);
            throw new DaoException("Error finding user by id", e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_EMAIL)) {

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
            logger.error("Error finding user by email {}", e.getMessage());
            throw new DaoException("Error finding user by email", e);
        }
    }

    @Override
    public boolean createUser(User user) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_USER)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getRole().name());
            preparedStatement.setString(6, user.getStatus().name());
            preparedStatement.setDouble(7, user.getBalance());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error create user {}", e.getMessage());
            throw new DaoException("Error creating in DB", e);
        }
    }
}
