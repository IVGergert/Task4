package com.gergert.task4.controller.command;

import com.gergert.task4.model.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request) throws ServiceException;
}
