package com.nnk.springboot.service.user_service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.exception.ResourcesNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * UserServiceImpl: CRUD
 * @author Subhi
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /**
     * Get the all Users from DDB
     * @return List of User
     */
    @Override
    public List<User> getUserList() {
        logger.debug("This getUserList(from UserServiceImpl) starts here.");
        List<User> users = userRepository.findAll();

        if(users.isEmpty()){
            logger.info("Users is empty in DDB!(from getUserList, UserServiceImpl).");
            return new ArrayList<>();
        }
        logger.info("Users successfully loaded from DDB(from getUserList, UserServiceImpl).");
        return users;
    }
    /**
     * Save a new User
     * @param user User
     * @return User
     */
    @Override
    public User saveNewUser(User user) {
        logger.debug("This saveNewUser(from UserServiceImpl) starts here.");
        User savedUser = userRepository.save(user);
        logger.info("New User successfully saved into DDB with id: {} (from saveNewUser, UserServiceImpl).", savedUser.getId());
        return savedUser;
    }
    /**
     * Find User by its Id and return if User exists
     * @param id Integer
     * @return User
     */
    @Override
    public User getUserById(Integer id) {
        logger.debug("This getUserById(from UserServiceImpl) starts here.");
        User user = getUserByUserId(id);
        logger.info("User successfully found by its id: {} (from getUserById, UserServiceImpl).", id);
        return user;
    }
    /**
     * Update a User if its id exists in DDB
     * @param user User
     */
    @Override
    public void updateUser(User user) {
        logger.info("User which id: {} successfully updated(from updateUser, UserServiceImpl).", user.getId());
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        logger.debug("This deleteUserById(from UserServiceImpl starts here.) ");
        User userById = getUserByUserId(id);
        if(userById != null) {
            logger.info("User which id: {} successfully deleted from DDB(from UserServiceImpl)", id);
            userRepository.deleteById(id);
        }
    }

    private User getUserByUserId(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            logger.error("This userId: {} not found!", id);
            throw new ResourcesNotFoundException("This User doesn't exist with this id : " + id + " , from getUserByUserId, BidListServiceImpl.");
        });
        return user;
    }
}
