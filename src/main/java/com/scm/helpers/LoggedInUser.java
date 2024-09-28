package com.scm.helpers;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;

// fetching data from logged user
// accessing data to save the profile page
public class LoggedInUser {
    public static String getEmailOfLoggedInUser(Authentication authentication){

        //Note: when user login in Email and password that user name is Email but
        // in login with google that time user name is ID following method is accessing Email

        //check user is loging with google or not
        if(authentication instanceof OAuth2AuthenticationToken){
            var rOAuth2AuthenticationToken=(OAuth2AuthenticationToken) authentication;
            var clientId= rOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

           var oauth2User= (OAuth2User)authentication.getPrincipal(); //fetching data when login with google
           String username="";
            //when login with google that time fetching email
            if(clientId.equalsIgnoreCase("google")){
                System.out.println("Getting email from google");
              username=oauth2User.getAttribute("email").toString();

            }
            return username;

        }else {
         //when email and password are login that time : fetching email

            System.out.println("Getting email from local database");
           return  authentication.getName();
        }
    }
}
