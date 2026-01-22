package com.gergert.task4.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncryptorTest {

    @Test
    void encrypt() {
        String plainPassword = "myPassword";

        String hashedPassword = PasswordEncryptor.encrypt(plainPassword);

        assertNotNull(hashedPassword);
        assertNotEquals(plainPassword, hashedPassword);

        boolean result = PasswordEncryptor.checkHashPassword(plainPassword, hashedPassword);

        assertTrue(result);
    }

    @Test
    void checkPassword_ShouldReturnFalse_WhenPasswordIsWrong() {
        String correctPassword = "admin";
        String wrongPassword = "user";

        String hashedPassword = PasswordEncryptor.encrypt(correctPassword);

        boolean result = PasswordEncryptor.checkHashPassword(wrongPassword, hashedPassword);

        assertFalse(result);
    }

    @Test
    void encrypt_ShouldGenerateDifferentHashes_ForSamePassword() {
        String password = "samePassword";

        String hash1 = PasswordEncryptor.encrypt(password);
        String hash2 = PasswordEncryptor.encrypt(password);

        assertNotNull(hash1);
        assertNotNull(hash2);
        assertNotEquals(hash1, hash2);
    }
}