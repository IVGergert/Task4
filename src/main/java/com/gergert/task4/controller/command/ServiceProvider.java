package com.gergert.task4.controller.command;

import com.gergert.task4.model.service.UserService;
import com.gergert.task4.model.service.impl.UserServiceImpl;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();
    private final UserService userService = new UserServiceImpl();

    private ServiceProvider() {}

    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }
}
