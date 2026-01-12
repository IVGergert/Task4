package com.gergert.task4.model.service;

import com.gergert.task4.model.entity.User;
import com.gergert.task4.model.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> signIn (String email, String password) throws ServiceException;
    boolean register(User user) throws ServiceException;
    List<User> findAllUsers() throws ServiceException;
}
