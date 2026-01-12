package com.gergert.task4.controller.command;

import com.gergert.task4.model.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse resp) throws ServiceException;
}
