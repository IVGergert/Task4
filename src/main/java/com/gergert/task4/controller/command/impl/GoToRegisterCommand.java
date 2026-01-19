package com.gergert.task4.controller.command.impl;

import com.gergert.task4.controller.command.Command;
import com.gergert.task4.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

import static com.gergert.task4.controller.command.PathConstant.REGISTER_PAGE;

public class GoToRegisterCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(REGISTER_PAGE, Router.RouterType.FORWARD);
    }
}
