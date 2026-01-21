package com.gergert.task4.controller.command.impl;

import com.gergert.task4.controller.command.Command;
import com.gergert.task4.controller.command.Router;
import com.gergert.task4.model.entity.User;
import com.gergert.task4.model.exception.ServiceException;
import com.gergert.task4.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.gergert.task4.controller.command.AttributeConstant.USERS;
import static com.gergert.task4.controller.command.PathConstant.ADMIN_PAGE;

public class AdminCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService;

    public AdminCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        logger.info("Opening admin page");

        List<User> users = userService.findAllUsers();
        request.setAttribute(USERS, users);
        return new Router(ADMIN_PAGE, Router.RouterType.FORWARD);
    }
}
