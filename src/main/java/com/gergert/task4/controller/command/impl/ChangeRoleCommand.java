package com.gergert.task4.controller.command.impl;

import com.gergert.task4.controller.command.Command;
import com.gergert.task4.controller.command.Router;
import com.gergert.task4.model.exception.ServiceException;
import com.gergert.task4.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

import static com.gergert.task4.controller.command.AttributeConstant.NEW_ROLE;
import static com.gergert.task4.controller.command.AttributeConstant.USER_ID;
import static com.gergert.task4.controller.command.PathConstant.REDIRECT_ADMIN;
import static com.gergert.task4.controller.command.Router.RouterType.REDIRECT;

public class ChangeRoleCommand implements Command {
    private final UserService userService;

    public ChangeRoleCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String userId = request.getParameter(USER_ID);
        String newRole = request.getParameter(NEW_ROLE);

        if (userId != null && newRole != null){
            userService.changeUserRole(Long.parseLong(userId), newRole);
        }

        return new Router(REDIRECT_ADMIN, REDIRECT);
    }
}
