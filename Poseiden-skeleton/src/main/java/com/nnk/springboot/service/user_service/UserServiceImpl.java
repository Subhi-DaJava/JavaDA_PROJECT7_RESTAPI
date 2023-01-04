package com.nnk.springboot.service.user_service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.dto.UserGetDTO;
import com.nnk.springboot.dto.UserSaveDTO;
import com.nnk.springboot.exception.ResourcesNotFoundException;
import com.nnk.springboot.mapper.MapperService;
import com.nnk.springboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserServiceImpl: CRUD
 * @author Subhi
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;
    private MapperService mapperService;

    public UserServiceImpl(UserRepository userRepository, MapperService mapperService) {
        this.userRepository = userRepository;
        this.mapperService = mapperService;
    }
    /**
     * Get the all Users from DDB and transfer to UserGetDTOs
     * @return List of UserGetDTO
     */
    @Override
    public List<UserGetDTO> getUserList() {
        logger.debug("This getUserList(from UserServiceImpl) starts here.");
        List<UserGetDTO> userGetDTOs;
        List<User> users = userRepository.findAll();

        if(users.isEmpty()){
            logger.info("Users is empty in DDB!(from getUserList, UserServiceImpl).");
            return new ArrayList<>();
        }
        logger.info("Users successfully loaded from DDB(from getUserList, UserServiceImpl).");
        userGetDTOs = users.stream().map(user ->
                mapperService.fromUser(user)
        ).collect(Collectors.toList());
        return userGetDTOs;
    }
    /**
     * Save a new User via UserSaveDTO
     * @param userSaveDTO UserSaveDTO
     * @return UserGetDTO
     */
    @Override
    public UserGetDTO saveNewUser(UserSaveDTO userSaveDTO) {
        logger.debug("This saveNewUser(from UserServiceImpl) starts here.");
        User user = mapperService.fromUserSaveDTO(userSaveDTO);
        User savedUser = userRepository.save(user);
        logger.info("New User successfully saved into DDB(from saveNewUser, UserServiceImpl).");
        UserGetDTO returnUserGetDTO = mapperService.fromUser(savedUser);
        return returnUserGetDTO;
    }
    /**
     * Find User by its Id and return UserGetDTO
     * @param id Integer
     * @return UserGetDTO
     */
    @Override
    public UserGetDTO getUserById(Integer id) {
        logger.debug("This getUserById(from UserServiceImpl) starts here.");
        User user = getUserByUserId(id);

        logger.info("User successfully found by its id: {} (from getUserById, UserServiceImpl).", id);
        return mapperService.fromUser(user);
    }
    /**
     * Update a User if its id exists in DDB
     * @param userSaveDTO UserSaveDTO
     */
    @Override
    public void updateUser(UserSaveDTO userSaveDTO) {
        User updateUser = mapperService.fromUserSaveDTO(userSaveDTO);
        updateUser.setId(userSaveDTO.getUserId());

        logger.info("User which id: {} successfully updated(from updateUser, UserServiceImpl).", userSaveDTO.getUserId());
        userRepository.save(updateUser);
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
