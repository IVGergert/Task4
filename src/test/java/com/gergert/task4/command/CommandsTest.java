package com.gergert.task4.command;

import com.gergert.task4.controller.Controller;
import com.gergert.task4.controller.command.Command;
import com.gergert.task4.controller.command.Router;
import com.gergert.task4.controller.command.impl.*;
import com.gergert.task4.model.entity.User;
import com.gergert.task4.model.entity.UserRole;
import com.gergert.task4.model.exception.ServiceException;
import com.gergert.task4.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.gergert.task4.controller.command.AttributeConstant.*;
import static com.gergert.task4.controller.command.CommandType.GO_TO_LOGIN;
import static com.gergert.task4.controller.command.PathConstant.*;
import static com.gergert.task4.controller.command.Router.RouterType.REDIRECT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandsTest{
    @Mock private UserService userService;
    @Mock private HttpServletRequest request;
    @Mock private HttpSession session;

    @Test
    void loginCommand_Success() throws ServiceException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter(EMAIL)).thenReturn("admin@mail.com");
        when(request.getParameter(PASSWORD)).thenReturn("123");
        when(request.getSession(true)).thenReturn(session);

        User admin = new User();
        admin.setRole(UserRole.ADMIN);
        when(userService.login("admin@mail.com", "123")).thenReturn(Optional.of(admin));

        Command command = new LoginCommand(userService);
        Router router = command.execute(request);

        assertEquals(REDIRECT_ADMIN, router.getPath());
        verify(session).setAttribute(USER, admin);
    }

    @Test
    void logoutCommand() throws ServiceException {
        when(request.getSession(false)).thenReturn(session);

        Command command = new LogoutCommand();
        Router router = command.execute(request);

        assertEquals(REDIRECT_LOGIN, router.getPath());
        verify(session).invalidate();
    }

    @Test
    void registerCommand_Success() throws ServiceException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter(EMAIL)).thenReturn("new@mail.com");
        when(request.getParameter(PASSWORD)).thenReturn("123");

        when(userService.register(anyString(), anyString(), any(), any())).thenReturn(true);

        Command command = new RegisterCommand(userService);
        Router router = command.execute(request);

        assertEquals(REDIRECT_LOGIN, router.getPath());
        assertEquals(REDIRECT, router.getRouteType());
    }

    @Test
    void banUserCommand() throws ServiceException {
        when(request.getParameter("userId")).thenReturn("5");

        Command command = new BanUserCommand(userService);
        Router router = command.execute(request);

        assertEquals(REDIRECT_ADMIN, router.getPath());
        verify(userService).banUser(5L);
    }

    @Test
    void unbanUserCommand() throws ServiceException {
        when(request.getParameter("userId")).thenReturn("5");

        Command command = new UnbanUserCommand(userService);
        Router router = command.execute(request);

        assertEquals(REDIRECT_ADMIN, router.getPath());
        verify(userService).unBunUser(5L);
    }

    @Test
    void deleteUserCommand() throws ServiceException {
        when(request.getParameter("userId")).thenReturn("5");

        Command command = new DeleteUserCommand(userService);
        Router router = command.execute(request);

        assertEquals(REDIRECT_ADMIN, router.getPath());
        verify(userService).deleteUser(5L);
    }

    @Test
    void changeRoleCommand() throws ServiceException {
        when(request.getParameter("userId")).thenReturn("5");
        when(request.getParameter("newRole")).thenReturn("ADMIN");

        Command command = new ChangeRoleCommand(userService);
        Router router = command.execute(request);

        assertEquals(REDIRECT_ADMIN, router.getPath());
        verify(userService).changeUserRole(5L, "ADMIN");
    }
}
