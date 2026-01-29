package com.gergert.task4.controller.filter;

import com.gergert.task4.controller.command.CommandType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

import static com.gergert.task4.controller.command.AttributeConstant.COMMAND;
import static com.gergert.task4.controller.command.AttributeConstant.USER;
import static com.gergert.task4.controller.command.PathConstant.REDIRECT_LOGIN;

@WebFilter(urlPatterns = "/controller")
public class AuthFilter implements Filter {

    private static final Set<String> GUEST_COMMANDS = Set.of(
            CommandType.LOGIN.name(),
            CommandType.REGISTER.name(),
            CommandType.GO_TO_LOGIN.name(),
            CommandType.GO_TO_REGISTER.name()
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {


        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession(false);

        String commandName = req.getParameter(COMMAND);

        if (commandName == null){
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        boolean isUserLoggedIn = (session != null && session.getAttribute(USER) != null);
        boolean isGuestCommand = GUEST_COMMANDS.contains(commandName.toUpperCase());

        if (!isUserLoggedIn && !isGuestCommand) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        chain.doFilter(servletRequest, servletResponse);
    }
}
