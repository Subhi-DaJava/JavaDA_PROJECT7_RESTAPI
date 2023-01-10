package com.nnk.springboot.service.user_service;

import com.nnk.springboot.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * UserService allows to insert the business logic
 * in the User business domain.
 * @author Subhi
 */
public interface UserService {
    List<User> getUserList();
    User saveNewUser(User user);

    User getUserById(Integer id);

    void updateUser(User user);

    void deleteUserById(Integer id);

    Optional<User> findByUsername(String username);

}
