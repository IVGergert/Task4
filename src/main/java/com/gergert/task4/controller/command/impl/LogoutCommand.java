package com.gergert.task4.controller.command.impl;

import com.gergert.task4.controller.command.Command;
import com.gergert.task4.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gergert.task4.controller.command.PathConstant.*;
import static com.gergert.task4.controller.command.Router.RouterType.REDIRECT;

public class LogoutCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        logger.info("Invalidating session");

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return new Router(CMD_LOGIN, REDIRECT);
    }
}
