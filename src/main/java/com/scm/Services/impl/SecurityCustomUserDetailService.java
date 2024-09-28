package com.scm.Services.impl;

import com.scm.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityCustomUserDetailService  implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    // user to load//access user data from database using loadUserByUsername
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username)
     .orElseThrow(()->new UsernameNotFoundException("User not found with email: " +username));
    }
}
