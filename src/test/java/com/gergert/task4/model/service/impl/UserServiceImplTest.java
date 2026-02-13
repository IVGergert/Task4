package com.gergert.task4.model.service.impl;

import com.gergert.task4.model.dao.UserDao;
import com.gergert.task4.model.entity.User;
import com.gergert.task4.model.entity.UserRole;
import com.gergert.task4.model.entity.UserStatus;
import com.gergert.task4.model.exception.DaoException;
import com.gergert.task4.model.exception.ServiceException;
import com.gergert.task4.model.factory.UserFactory;
import com.gergert.task4.model.service.UserService;
import com.gergert.task4.util.PasswordEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserDao userDao;

    @Mock
    private UserFactory userFactory;

    private UserService userService;

    @BeforeEach
    void setUp(){
        userService = new UserServiceImpl(userDao, userFactory);
    }

    @Test
    void loginWhenUserIsACTIVE() throws DaoException, ServiceException {
        String email = "test@mail.com";
        String password = "123";
        String hash = "hashed_123";

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail(email);
        mockUser.setPassword(hash);
        mockUser.setStatus(UserStatus.ACTIVE);
        mockUser.setRole(UserRole.USER);

        when(userDao.findUserByEmail(email)).thenReturn(Optional.of(mockUser));

        try (MockedStatic<PasswordEncryptor> utilities = mockStatic(PasswordEncryptor.class)) {
            utilities.when(() -> PasswordEncryptor.checkHashPassword(password, hash)).thenReturn(true);

            Optional<User> result = userService.login(email, password);

            assertTrue(result.isPresent());
            assertEquals(email, result.get().getEmail());
            assertNull(result.get().getPassword());
        }
    }

    @Test
    void loginWhenUserIsBanned() throws DaoException {
        String email = "banned@mail.com";
        String password = "123";

        User bannedUser = new User();
        bannedUser.setEmail(email);
        bannedUser.setPassword("hash");
        bannedUser.setStatus(UserStatus.BANNED);

        when(userDao.findUserByEmail(email)).thenReturn(Optional.of(bannedUser));

        try (MockedStatic<PasswordEncryptor> utilities = mockStatic(PasswordEncryptor.class)) {
            utilities.when(() -> PasswordEncryptor.checkHashPassword(anyString(), anyString())).thenReturn(true);

            ServiceException exception = assertThrows(ServiceException.class, () -> {
                userService.login(email, password);
            });

            assertTrue(exception.getMessage().contains("is_blocked") || exception.getMessage().contains("USER_BANNED"));
        }
    }

    @Test
    void registerSuccess() throws DaoException, ServiceException {
        String email = "new@mail.com";
        when(userDao.findUserByEmail(email)).thenReturn(Optional.empty());

        User newUser = new User();

        when(userFactory.createUser(anyString(), anyString(), anyString(), anyString())).thenReturn(newUser);

        when(userDao.createUser(newUser)).thenReturn(true);

        try (MockedStatic<PasswordEncryptor> utilities = mockStatic(PasswordEncryptor.class)) {
            utilities.when(() -> PasswordEncryptor.encrypt(anyString())).thenReturn("hashed_pass");

            boolean result = userService.register(email, "pass", "John", "Doe");
            assertTrue(result);
            verify(userDao).createUser(newUser);
        }
    }

    @Test
    void registerFailIfUserExists() throws DaoException, ServiceException {
        String email = "exist@mail.com";
        when(userDao.findUserByEmail(email)).thenReturn(Optional.of(new User()));

        boolean result = userService.register(email, "pass", "John", "Doe");
        assertFalse(result);
        verify(userDao, never()).createUser(any());
    }

    @Test
    void findAllUsers() throws DaoException, ServiceException {
        List<User> mockList = Arrays.asList(new User(), new User());
        when(userDao.findAll()).thenReturn(mockList);

        List<User> result = userService.findAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userDao).findAll();
    }

    @Test
    void deleteUser() throws DaoException, ServiceException {
        long userId = 5L;
        when(userDao.deleteUser(userId)).thenReturn(true);

        boolean result = userService.deleteUser(userId);

        assertTrue(result);
        verify(userDao).deleteUser(userId);
    }

    @Test
    void banUser() throws DaoException, ServiceException {
        long userId = 10L;
        when(userDao.changeStatusUser(userId, UserStatus.BANNED)).thenReturn(true);

        boolean result = userService.banUser(userId);

        assertTrue(result);
        verify(userDao).changeStatusUser(userId, UserStatus.BANNED);
    }


    @Test
    void unbanUser() throws DaoException, ServiceException {
        long userId = 10L;
        when(userDao.changeStatusUser(userId, UserStatus.ACTIVE)).thenReturn(true);

        boolean result = userService.unBunUser(userId);

        assertTrue(result);
        verify(userDao).changeStatusUser(userId, UserStatus.ACTIVE);
    }

    @Test
    void changeUserRoleSuccess() throws DaoException, ServiceException {
        long userId = 7L;
        String newRoleStr = "ADMIN";

        when(userDao.changeRoleUser(userId, UserRole.ADMIN)).thenReturn(true);

        boolean result = userService.changeUserRole(userId, newRoleStr);

        assertTrue(result);
        verify(userDao).changeRoleUser(userId, UserRole.ADMIN);
    }

    @Test
    void changeUserRoleInvalidString() throws ServiceException, DaoException {
        boolean result = userService.changeUserRole(1L, "INVALID_ROLE");

        assertFalse(result);
        verify(userDao, never()).changeRoleUser(anyLong(), any());
    }

    @Test
    void findAllUsersThrowsServiceException() throws DaoException {
        when(userDao.findAll()).thenThrow(new DaoException("DB Connection failed"));

        assertThrows(ServiceException.class, () -> userService.findAllUsers());
    }
}