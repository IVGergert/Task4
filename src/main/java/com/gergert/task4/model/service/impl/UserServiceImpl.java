package com.gergert.task4.model.service.impl;

import com.gergert.task4.model.dao.UserDao;
import com.gergert.task4.model.dao.impl.UserDaoImpl;
import com.gergert.task4.model.entity.User;
import com.gergert.task4.model.exception.ServiceException;
import com.gergert.task4.model.exception.DaoException;
import com.gergert.task4.model.factory.UserFactory;
import com.gergert.task4.model.factory.impl.UserFactoryImpl;
import com.gergert.task4.model.service.UserService;
import com.gergert.task4.util.FieldValidator;
import com.gergert.task4.util.PasswordEncryptor;
import com.gergert.task4.util.impl.FieldValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private static final UserService INSTANCE = new UserServiceImpl();

    private final UserDao userDao = new UserDaoImpl();
    private final FieldValidator validator = new FieldValidatorImpl();
    private final UserFactory userFactory = new UserFactoryImpl();

    public static UserService getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<User> login(String email, String password) throws ServiceException {
        if (!validator.validateLoginInput(email, password)) {
            logger.warn("Login failed: All fields must be filled in");
            return Optional.empty();
        }

        try {
            Optional<User> userOptinal = userDao.findUserByEmail(email);

            if (userOptinal.isPresent()){
                User user = userOptinal.get();

                if (PasswordEncryptor.checkHashPassword(password, user.getPassword())){
                    logger.info("User with ID = {}, Email = {} logged in successfully", user.getId(), user.getEmail());
                    user.setPassword(null);
                    return Optional.of(user);
                }
            }

            logger.warn("Login failed: Invalid email or password for {}", email);
            return Optional.empty();
        } catch (DaoException e) {
            logger.error("DB error during sign in for email: {}", email, e);
            throw new ServiceException("System error, try again later", e);
        }
    }

    @Override
    public boolean register(String email, String password, String firstName, String lastName) throws ServiceException {
        logger.info("Start of user creation for email: {}", email);

        if (!validator.validateRegistrationInput(email, password, firstName, lastName)) {
            logger.warn("Registration failed: All fields must be filled in");
            throw new ServiceException("All fields must be filled in");
        }

        try {
            Optional<User> userOptional = userDao.findUserByEmail(email);

            if (userOptional.isPresent()) {
                logger.warn("Registration failed: Email {} already exists", email);
                throw new ServiceException("Email already exists");
            }

            String hashPassword = PasswordEncryptor.encrypt(password);
            User user = userFactory.createUser(email, hashPassword, firstName, lastName);

            boolean isCreated = userDao.createUser(user);
            logger.info("User registration result for {}", email);
            return isCreated;

        } catch (DaoException e) {
            logger.error("DB error during registration for email: {}", email, e);
            throw new ServiceException("System error during registration", e);
        }
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        try {
            List<User> users = userDao.findAll();
            logger.debug("Found {} users", users.size());
            return users;
        } catch (DaoException e){
            logger.error("Error finding all users", e);
            throw new ServiceException("System error while fetching users", e);
        }
    }
}
