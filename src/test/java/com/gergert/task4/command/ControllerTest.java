package com.gergert.task4.command;

import com.gergert.task4.controller.Controller;
import com.gergert.task4.controller.command.Command;
import com.gergert.task4.controller.command.CommandType;
import com.gergert.task4.controller.command.Router;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;


import static com.gergert.task4.controller.command.AttributeConstant.COMMAND;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ControllerTest  extends Controller  {
    @Mock HttpServletRequest request;
    @Mock HttpServletResponse response;
    @Mock RequestDispatcher dispatcher;
    @Mock Command command;

    @Test
    void doGet_ShouldForward_WhenRouterIsForward(){
        try (MockedStatic<CommandType> factory = mockStatic(CommandType.class)) {
            factory.when(() -> CommandType.define("LOGIN")).thenReturn(command);

            when(request.getParameter(COMMAND)).thenReturn("LOGIN");

            Router router = new Router("/page.jsp", Router.RouterType.FORWARD);
            when(command.execute(request)).thenReturn(router);

            when(request.getRequestDispatcher("/page.jsp")).thenReturn(dispatcher);

            super.doGet(request, response);

            verify(dispatcher).forward(request, response);
        } catch (Exception e) {
        }
    }

    @Test
    void doPost_ShouldRedirect_WhenRouterIsRedirect(){
        try (MockedStatic<CommandType> factory = mockStatic(CommandType.class)) {
            factory.when(() -> CommandType.define("LOGIN")).thenReturn(command);

            when(request.getParameter(COMMAND)).thenReturn("LOGIN");
            when(request.getContextPath()).thenReturn("/app");

            Router router = new Router("/home", Router.RouterType.REDIRECT);
            when(command.execute(request)).thenReturn(router);

            super.doPost(request, response);

            verify(response).sendRedirect("/app/home");
        } catch (Exception e) {
        }
    }
}
