package com.gergert.task4;

import com.gergert.task4.model.exception.ServiceException;
import com.gergert.task4.model.service.impl.UserServiceImpl;

import java.util.Scanner;


public class Mian {

    public static void main(String[] args) throws ServiceException {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите email:");
        String email = scanner.nextLine();
        System.out.println("Введите email:");
        String password = scanner.nextLine();

        UserServiceImpl service = new UserServiceImpl();
        service.signIn(email, password);

        System.out.println("Вы успешно вошли");


    }
}
