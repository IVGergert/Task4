package com.gergert.task4.model.service.impl;

import com.gergert.task4.model.dao.impl.UserDaoImpl;
import com.gergert.task4.model.entity.User;
import com.gergert.task4.model.exception.ServiceException;
import com.gergert.task4.model.exception.DaoException;
import com.gergert.task4.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger();
    private final UserDaoImpl userDAO = new UserDaoImpl();

    @Override
    public Optional<User> signIn(String email, String password) throws ServiceException {
        try {
            Optional<User> opt = userDAO.findUserByEmail(email);
            if (opt.isPresent()){
                User user = opt.get();
                if (BCrypt.checkpw(password, user.getPassword())){
                    logger.info("User with email {} successfully sign in", user.getEmail());
                    return Optional.of(user);
                }
            }
        } catch (DaoException e) {
            logger.info("Error when logging in {}", e);
            throw new ServiceException("Error when logging in", e);
        }

        return Optional.empty();
    }

    @Override
    public boolean register(User user) throws ServiceException {
        try{
            if (userDAO.emailExists(user.getEmail())){
                return false;
            }
            String hashPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            user.setPassword(hashPassword);
            userDAO.createUser(user);
            return true;
        } catch (DaoException e){
            logger.info("Error when registration");
            throw new ServiceException("Error when registration {}", e);
        }
    }

    @Override
    public List<User> findAllUsers() throws ServiceException {
        try {
            logger.info("All users have been successfully displayed");
            return userDAO.findAll();
        } catch (DaoException e){
            logger.info("Error when outputting all users");
            throw new ServiceException("Error when registration {}", e);
        }
    }
}
