package com.gergert.task4.controller.command;

import com.gergert.task4.controller.command.impl.*;
import com.gergert.task4.model.service.impl.UserServiceImpl;

public enum CommandType {
    LOGIN (new LoginCommand(UserServiceImpl.getInstance())),
    REGISTER (new RegisterCommand(UserServiceImpl.getInstance())),
    LOGOUT (new LogoutCommand()),

    GO_TO_LOGIN (new GoToLoginCommand()),
    GO_TO_REGISTER (new GoToRegisterCommand()),

    ADMIN_PAGE (new AdminCommand(UserServiceImpl.getInstance())),
    HOME_PAGE (new UserCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command define(String commandName) {
        if (commandName == null || commandName.isEmpty()) {
            return GO_TO_LOGIN.command;
        }

        try {
            return CommandType.valueOf(commandName.toUpperCase()).command;
        } catch (IllegalArgumentException e) {
            return GO_TO_LOGIN.command;
        }
    }
}
