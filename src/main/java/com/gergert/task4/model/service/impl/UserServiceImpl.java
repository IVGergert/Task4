package com.gergert.task4.model.service.impl;

import com.gergert.task4.model.dao.UserDao;
import com.gergert.task4.model.dao.impl.UserDaoImpl;
import com.gergert.task4.model.entity.User;
import com.gergert.task4.model.entity.UserRole;
import com.gergert.task4.model.entity.UserStatus;
import com.gergert.task4.model.exception.ServiceException;
import com.gergert.task4.model.exception.DaoException;
import com.gergert.task4.model.factory.UserFactory;
import com.gergert.task4.model.factory.impl.UserFactoryImpl;
import com.gergert.task4.model.service.UserService;
import com.gergert.task4.util.PasswordEncryptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService INSTANCE = new UserServiceImpl();

    private final UserDao userDao;
    private final UserFactory userFactory;

    public static UserService getInstance() {
        return INSTANCE;
    }

    private UserServiceImpl() {
        this.userDao = new UserDaoImpl();
        this.userFactory = new UserFactoryImpl();
    }

    public UserServiceImpl(UserDao userDao, UserFactory userFactory) {
        this.userDao = userDao;
        this.userFactory = userFactory;
    }

    @Override
    public Optional<User> login(String email, String password) throws ServiceException {
        logger.debug("Login attempt for email: {}", email);

        try {
            Optional<User> userOptinal = userDao.findUserByEmail(email);

            if (userOptinal.isPresent()) {
                User user = userOptinal.get();

                if (PasswordEncryptor.checkHashPassword(password, user.getPassword())) {

                    if (user.getStatus() == UserStatus.BANNED) {
                        logger.warn("Login attempt denied: User {} is BANNED", email);
                        throw new ServiceException("is_blocked");
                    }

                    logger.info("User with ID = {}, Email = {} logged in successfully", user.getId(), user.getEmail());
                    user.setPassword(null);
                    return Optional.of(user);
                }
            }

            logger.warn("Login failed: Invalid email or password for {}", email);
            return Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException("System error, try again later", e);
        }
    }

    @Override
    public boolean register(String email, String password, String firstName, String lastName) throws ServiceException {
        logger.debug("Registration attempt for email: {}", email);

        try {
            Optional<User> userOptional = userDao.findUserByEmail(email);

            if (userOptional.isPresent()) {
                logger.warn("Registration failed: Email {} already exists", email);
                return false;
            }

            String hashPassword = PasswordEncryptor.encrypt(password);
            User user = userFactory.createUser(email, hashPassword, firstName, lastName);

            logger.info("User successfully registration for {}", email);
            return userDao.createUser(user);
        } catch (DaoException e) {
            throw new ServiceException("System error during registration", e);
        }
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        try {
            List<User> users = userDao.findAll();
            logger.debug("Found {} users from DB.", users.size());
            return users;
        } catch (DaoException e) {
            throw new ServiceException("System error while fetching users", e);
        }
    }

    @Override
    public boolean deleteUser(long userId) throws ServiceException {
        try {
            if (userDao.deleteUser(userId)) {
                logger.info("User with id={} delete successfully", userId);
                return true;
            }

            logger.info("User with id={} delete failed", userId);
            return false;
        } catch (DaoException e) {
            throw new ServiceException("System error while fetching users", e);
        }
    }

    @Override
    public boolean banUser(long userId) throws ServiceException {
        try {
            if (userDao.changeStatusUser(userId, UserStatus.BANNED)) {
                logger.info("User with id = {} status changed to BANNED successfully", userId);
                return true;
            }

            logger.warn("Failed to ban user id = {}", userId);
            return false;
        } catch (DaoException e) {
            throw new ServiceException("System error while ban user", e);
        }
    }

    @Override
    public boolean unBunUser(long userId) throws ServiceException {
        try {
            if (userDao.changeStatusUser(userId, UserStatus.ACTIVE)) {
                logger.info("User with id = {} status changed to ACTIVE successfully", userId);
                return true;
            }

            logger.warn("Failed to unban user id = {}", userId);
            return false;
        } catch (DaoException e) {
            throw new ServiceException("System error while unbanning user", e);
        }
    }

    @Override
    public boolean changeUserRole(long userId, String role) throws ServiceException {
        try {
            UserRole newRole = UserRole.valueOf(role);

            if (userDao.changeRoleUser(userId, newRole)) {
                logger.info("User with id = {} role changed successfully to {}", userId, newRole);
                return true;
            }

            logger.warn("Failed to change user role with id = {}", userId);
            return false;
        } catch (DaoException e) {
            throw new ServiceException("System error while change user role", e);
        }
    }
}
