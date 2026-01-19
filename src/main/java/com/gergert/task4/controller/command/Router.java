package com.gergert.task4.controller.command;

public class Router {

    public enum RouterType {
        FORWARD, REDIRECT
    }

    private final String path;
    private final RouterType routerType;

    public Router(String path, RouterType routerType) {
        this.path = path;
        this.routerType = routerType;
    }

    public String getPath() {
        return path;
    }

    public RouterType getRouteType() {
        return routerType;
    }


}
