package com.scm.Services.impl;

import com.scm.Entity.Users;
import com.scm.Repository.UserRepo;
import com.scm.Services.UserService;
import com.scm.helpers.AppConstant;
import com.scm.helpers.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public Users saveUser(Users user) {
        user.setUId(user.getUId());

        //password encode
        //user.setpassword(uid)
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set the user role
        user.setRoleList(List.of(AppConstant.ROLE_USER));
        logger.info(user.getProviders().toString());
        return userRepo.save(user);
    }

    @Override
    public Optional<Users> getUserById(int id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<Users> updateUser(Users user) {
        Users user2=userRepo.findById(user.getUId()).orElseThrow(()->new ResourceNotFoundException("User Not Found") );
        //update user2 form user
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProviders(user.getProviders());
        user2.setProviderUserId(user.getProviderUserId());

        //save the user in database
        Users save=userRepo.save(user2);

        return Optional.ofNullable(save);


    }

    @Override
    public void deleteUser(int id) {
        Users user2=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException
                ("User Not Found") );
           userRepo.delete(user2);
    }

    @Override
    public boolean isUserExit(int id) {
       Users user2= userRepo.findById(id).orElse(null);
       return user2 !=null? true:false;
    }

    @Override
    public boolean isUserExitByEmail(String email) {
        Users user2= userRepo.findByEmail(email).orElse(null);
        return user2 !=null? true:false;
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }
}
