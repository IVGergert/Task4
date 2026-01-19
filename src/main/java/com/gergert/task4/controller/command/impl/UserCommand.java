package com.gergert.task4.controller.command.impl;

import com.gergert.task4.controller.command.Command;
import com.gergert.task4.controller.command.Router;
import com.gergert.task4.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gergert.task4.controller.command.AttributeConstant.EMAIL;
import static com.gergert.task4.controller.command.AttributeConstant.USER;
import static com.gergert.task4.controller.command.PathConstant.HOME_PAGE;
import static com.gergert.task4.controller.command.Router.RouterType.FORWARD;


public class UserCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Opening user home page");

        User user = (User) request.getSession().getAttribute(USER);
        request.setAttribute(EMAIL, user.getEmail());

        return new Router(HOME_PAGE, FORWARD);
    }
}
