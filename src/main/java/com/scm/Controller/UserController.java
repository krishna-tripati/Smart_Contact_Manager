package com.scm.Controller;

import com.scm.Entity.Users;
import com.scm.Services.UserService;
import com.scm.helpers.LoggedInUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.security.Principal;

//handing all user request
@Controller
@RequestMapping("/user")
public class UserController {

    //Loggers are crucial for debugging, monitoring, and tracking events within an application,
    // helping developers identify issues and improve performance.
   private Logger logger= LoggerFactory.getLogger(UserController.class);
   @Autowired
   private UserService userService;

    // User dashboard page
    @GetMapping("/dashboard")
    public String userDashboard(){
        return "/user/dashboard";
    }

    //user profile page
    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication){ //principal accessing data

//     String username = LoggedInUser.getEmailOfLoggedInUser(authentication);
//     logger.info("User logged in: {}",username);
//
//     //fetching data from database: get user from db: email,name,address
//       Users user= userService.getUserByEmail(username);
//        System.out.println(user.getName());
//        System.out.println(user.getEmail());
//
//        // send to the profile page
//        model.addAttribute("loggedInUser",user);

        return "user/profile";
    }







}
