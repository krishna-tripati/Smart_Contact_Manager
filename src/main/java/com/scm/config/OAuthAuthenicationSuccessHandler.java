package com.scm.config;

import com.scm.Entity.Providers;
import com.scm.Entity.Users;
import com.scm.Repository.UserRepo;
import com.scm.helpers.AppConstant;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


//this class add on Securityconfig oauth section
// when Google sign in then redirect to the dashboard page
@Component
public class OAuthAuthenicationSuccessHandler implements AuthenticationSuccessHandler {

  Logger logger= LoggerFactory.getLogger(OAuthAuthenicationSuccessHandler.class);
   @Autowired
   private UserRepo userRepo; // for saving email data to the database

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
       Authentication authentication) throws IOException, ServletException {

        logger.info("OAuthAuthenicationSuccessHandler");

        //fetching data from email when google login
        DefaultOAuth2User user= (DefaultOAuth2User) authentication.getPrincipal();

//         logger.info(user.getName());
//
//         user.getAttributes().forEach((key, value)->{
//           logger.info("{},{}",key,value);
//         });
//
//         logger.info(user.getAttributes().toString());

        //data database save //fetching data from email and save to the database
          String email=user.getAttribute("email").toString();
          String name=user.getAttribute("name").toString();
          String picture=user.getAttribute("picture").toString();
//          int UId= Integer.parseInt(user.getAttribute("UId").toString());
        

          //create user and save in database // this data save from google login
          Users user1=new Users();
          user1.setEmail(email);
          user1.setName(name);
          user1.setProfilePic(picture);
          user1.setPassword("password");
//          user1.setUId(UId); // already use in Users entity so not using here
          user1.setProviders(Providers.GOOGLE);

          user1.setEmailVerified(true);
          user1.setProviderUserId(user.getName());
          user1.setRoleList(List.of(AppConstant.ROLE_USER));
          user1.setAbout("This account created using Google....");

        // first check email is already exits in data or not
        // when email is not exit then save to the database
          Users user2=userRepo.findByEmail(email).orElse(null);
          if (user2==null){
              userRepo.save(user1);
              logger.info("User saved: "+ email);
          }

        new DefaultRedirectStrategy().sendRedirect(request,response,"/user/dashboard");

    }
}
