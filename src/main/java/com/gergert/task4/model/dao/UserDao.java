package com.gergert.task4.model.dao;

import com.gergert.task4.model.entity.User;
import com.gergert.task4.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAll() throws DaoException;
    Optional<User> findUserById(long id) throws DaoException;
    Optional<User> findUserByEmail(String email) throws DaoException;
    boolean emailExists(String email) throws DaoException;
    boolean createUser(User user) throws DaoException;
}
