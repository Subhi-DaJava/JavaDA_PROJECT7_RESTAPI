package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.user_service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
/**
 * UserController send requests to backend, getAllUserList, saveNewUser, getUserById, updateUserExisting, deleteUserExisting
 * @author Subhi
 */
@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    /**
     * Page list show all User
     * @param model Model
     * @return User page
     */
    @RequestMapping("/user/list")
    public String home(Model model) {
        logger.debug("This home(from UserController) starts here.");
        model.addAttribute("users", userService.getUserList());
        logger.info("User home page successfully loaded(from home, UserController).");

        return "user/list";
    }
    /**
     * Show Use addForm
     * @return UserAdd Page
     */
    @GetMapping("/user/add")
    public String addUserForm(User user) { // object user interact with add.html
        logger.debug("This addUserForm(from UserController) starts here.");
        return "user/add";
    }
    /**
     * Sava a User
     * @param user
     * @param result BindingResult
     * @return UserList Page
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.saveNewUser(user);
            logger.info("New User successfully saved in DDB(from validatePostMapping, UserController)");
            return "redirect:/user/list";
        }
        logger.error("result error= {}, (from validePostMapping, UserController)", result.getFieldErrors());
        return "user/add";
    }
    /**
     * Show updateForm
     * @param id Integer
     * @param model Model
     * @return UserUpdate Page with UserDTO
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.debug("This showUpdateForm(from UserController) starts here.");
        User user = userService.getUserById(id);
        user.setPassword("");
        model.addAttribute("user", user);
        return "user/update";
    }
    /**
     * Update a User by id, checking the fields before call to service
     * @param id Integer
     * @param user User
     * @param result BindingResult
     * @return UserList Page
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);

        userService.updateUser(user);
        logger.info("User with id: {} is successfully updated(from, updatePostMapping, UserController)", id);
        return "redirect:/user/list";
    }
    /**
     * Delete User by given UserId
     * @param id Integer
     * @return UserList Page
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        logger.debug("This deleteUser(from UserController) starts here.");
        userService.deleteUserById(id);
        logger.info("User successfully deleted by given Id: {}, from UserController.", id);
        return "redirect:/user/list";
    }
}
