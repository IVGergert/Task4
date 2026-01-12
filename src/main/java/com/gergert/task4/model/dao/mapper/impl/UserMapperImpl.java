package com.gergert.task4.model.dao.mapper.impl;

import com.gergert.task4.model.dao.mapper.EntityMapper;
import com.gergert.task4.model.entity.User;
import com.gergert.task4.model.entity.UserRole;
import com.gergert.task4.model.entity.UserStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapperImpl implements EntityMapper<User> {
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "first_name";
    private static final String ROLE = "role";
    private static final String LAST_NAME = "last_name";
    private static final String STATUS = "status";
    private static final String BALANCE = "balance";

    @Override
    public User map(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(ID));
        user.setEmail(rs.getString(EMAIL));
        user.setPassword(rs.getString(PASSWORD));
        user.setFirstName(rs.getString(FIRST_NAME));
        user.setLastName(rs.getString(LAST_NAME));
        user.setStatus(UserStatus.valueOf(rs.getString(STATUS)));
        user.setRole(UserRole.valueOf(rs.getString(ROLE)));
        user.setBalance(rs.getDouble(BALANCE));
        return user;
    }
}
