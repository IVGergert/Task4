package com.gergert.task4.model.dao;

import com.gergert.task4.model.entity.User;
import com.gergert.task4.model.entity.UserRole;
import com.gergert.task4.model.entity.UserStatus;
import com.gergert.task4.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    List<User> findAll() throws DaoException;
    Optional<User> findUserByEmail(String email) throws DaoException;
    boolean createUser(User user) throws DaoException;
    boolean deleteUser(long userId) throws DaoException;
    boolean changeStatusUser(long userId, UserStatus status) throws DaoException;
    boolean changeRoleUser(long userId, UserRole role) throws DaoException;
}
