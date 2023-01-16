package com.nnk.springboot.controllers;

import com.nnk.springboot.service.user_service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Subhi
 */
@Controller
public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);
    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Get login page
     * @return login or home page
     */
    @GetMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/secure/article-details")
    public String getAllUserArticles(Model model) {
        logger.debug("This getAllUserArticles(from LoginController) starts here.");
        model.addAttribute("users", userService.getUserList());
        logger.info("This getAllUserArticles(from LoginController) successfully load all users with this path starts here.");
        return "user/list";
    }

    @GetMapping("/403")
    public String error(Model model) {
        logger.debug("This error403(from LoginController) starts here.");
        String errorMessage= "You are not authorized for the requested data.";
        model.addAttribute("errorMsg", errorMessage);

        return "403";
    }


}
