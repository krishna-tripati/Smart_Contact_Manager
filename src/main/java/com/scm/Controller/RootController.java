package com.scm.Controller;

import com.scm.Entity.Users;
import com.scm.Services.UserService;
import com.scm.helpers.LoggedInUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

//Apply on all methods when user login
// User controller
@ControllerAdvice
public class RootController {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedUserInformation(Model model, Authentication authentication){

        if(authentication==null){
            return;
        }

        System.out.println("Adding logged in user information to the model");

        String username = LoggedInUser.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in: {}",username);

            Users user= userService.getUserByEmail(username);
            System.out.println(user);

            System.out.println(user.getName());
            System.out.println(user.getEmail());
            // send to the profile page
            model.addAttribute("loggedInUser",user);


    }

}
