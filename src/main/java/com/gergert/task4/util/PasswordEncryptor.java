package com.gergert.task4.util;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordEncryptor {

    private PasswordEncryptor() {}

    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean checkHashPassword(String userPassword, String dbPassword){
        return BCrypt.checkpw(userPassword, dbPassword);
    }
}
