package com.gergert.task4.controller.command.impl;

import com.gergert.task4.controller.command.Command;
import com.gergert.task4.controller.command.Router;
import com.gergert.task4.model.entity.User;
import com.gergert.task4.model.entity.UserRole;
import com.gergert.task4.model.exception.ServiceException;
import com.gergert.task4.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.gergert.task4.controller.command.AttributeConstant.*;
import static com.gergert.task4.controller.command.PathConstant.*;
import static com.gergert.task4.controller.command.Router.RouterType.FORWARD;
import static com.gergert.task4.controller.command.Router.RouterType.REDIRECT;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger();
    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        logger.info("Start LoginCommand");

        if ("GET".equalsIgnoreCase(request.getMethod())) {
            return new Router(LOGIN_PAGE, FORWARD);
        }

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        Optional<User> userOptional = userService.login(email, password);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            HttpSession session = request.getSession(true);
            session.setAttribute(USER, user);
            session.setAttribute(ROLE, user.getRole());

            if (user.getRole() == UserRole.ADMIN) {
                return new Router(REDIRECT_ADMIN, REDIRECT);
            } else {
                return new Router(REDIRECT_HOME, REDIRECT);
            }
        } else {
            logger.warn("Login failed for email: {}", email);
            request.setAttribute(ERROR_MSG, "Invalid email or password");
            request.setAttribute(EMAIL, email);
            return new Router(LOGIN_PAGE, FORWARD);
        }
    }
}
