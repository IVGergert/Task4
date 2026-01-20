package com.gergert.task4.controller.command.impl;

import com.gergert.task4.controller.command.Command;
import com.gergert.task4.controller.command.Router;
import com.gergert.task4.model.exception.ServiceException;
import com.gergert.task4.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gergert.task4.controller.command.AttributeConstant.*;
import static com.gergert.task4.controller.command.PathConstant.*;
import static com.gergert.task4.controller.command.Router.RouterType.FORWARD;
import static com.gergert.task4.controller.command.Router.RouterType.REDIRECT;

public class RegisterCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService;

    public RegisterCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        logger.info("Start RegisterCommand");

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            return new Router(REGISTER_PAGE, FORWARD);
        }

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);

        saveAttribute(request, email, firstName, lastName);

        logger.info("Attempting to register user: {}", email);

        if (userService.register(email, password, firstName, lastName)) {
            logger.info("New user registered: {}", email);
            return new Router(REDIRECT_LOGIN, REDIRECT);
        } else {
            logger.warn("Registration failed. User exists: {}", email);
            request.setAttribute(ERROR_MSG, "User with this email already exists.");
            saveAttribute(request, email, firstName, lastName);
            return new Router(REGISTER_PAGE, FORWARD);
        }
    }

    private void saveAttribute(HttpServletRequest request ,String email, String firstName, String lastName){
        request.setAttribute(EMAIL, email);
        request.setAttribute(FIRST_NAME, firstName);
        request.setAttribute(LAST_NAME, lastName);
    }
}
