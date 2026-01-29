package com.gergert.task4.controller;

import com.gergert.task4.controller.command.Command;
import com.gergert.task4.controller.command.CommandType;
import com.gergert.task4.controller.command.Router;
import com.gergert.task4.model.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.gergert.task4.controller.command.AttributeConstant.COMMAND;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        String commandName = request.getParameter(COMMAND);
        logger.debug("Executing command: {}", commandName);

        Command command = CommandType.define(commandName);

        try {
            Router router = command.execute(request);

            switch (router.getRouteType()) {
                case FORWARD -> request.getRequestDispatcher(router.getPath()).forward(request, response);
                case REDIRECT -> response.sendRedirect(request.getContextPath() + router.getPath());
            }
        } catch (ServiceException e) {
            logger.error("Service exception occurred", e);
            throw new ServletException(e);
        }
    }
}
