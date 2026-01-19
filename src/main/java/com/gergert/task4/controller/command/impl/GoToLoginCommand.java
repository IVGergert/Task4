package com.gergert.task4.controller.command.impl;

import com.gergert.task4.controller.command.Command;
import com.gergert.task4.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

import static com.gergert.task4.controller.command.PathConstant.LOGIN_PAGE;

public class GoToLoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request){
        return new Router(LOGIN_PAGE, Router.RouterType.FORWARD);
    }
}
