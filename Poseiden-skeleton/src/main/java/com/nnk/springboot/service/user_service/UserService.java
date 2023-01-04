package com.nnk.springboot.service.user_service;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.dto.UserGetDTO;
import com.nnk.springboot.dto.UserSaveDTO;

import java.util.List;
/**
 * UserService allows to insert the business logic
 * in the User business domain.
 * @author Subhi
 */
public interface UserService {
    List<UserGetDTO> getUserList();
    UserGetDTO saveNewUser(UserSaveDTO userSaveDTO);

    UserGetDTO getUserById(Integer id);

    void updateUser(UserSaveDTO userSaveDTO);

    void deleteUserById(Integer id);
}
