package com.gergert.task4.controller.command;

public final class PathConstant {
    private PathConstant() {}

    public static final String LOGIN_PAGE = "/WEB-INF/jsp/auth/login.jsp";
    public static final String REGISTER_PAGE = "/WEB-INF/jsp/auth/register.jsp";
    public static final String HOME_PAGE = "/WEB-INF/jsp/user/home.jsp";
    public static final String ADMIN_PAGE = "/WEB-INF/jsp/admin/admin.jsp";

    public static final String REDIRECT_LOGIN = "/controller?command=GO_TO_LOGIN";
    public static final String REDIRECT_HOME = "/controller?command=HOME_PAGE";
    public static final String REDIRECT_ADMIN = "/controller?command=ADMIN_PAGE";
}
