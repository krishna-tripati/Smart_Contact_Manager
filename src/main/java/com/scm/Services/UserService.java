package com.scm.Services;

import com.scm.Entity.Users;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Users saveUser(Users user);
    // Optional class or database is if data exit then access and data is not then showing exception
    Optional<Users> getUserById(int id);
    Optional<Users> updateUser(Users user);
    void deleteUser(int id);
    boolean isUserExit(int id);
    boolean isUserExitByEmail(String email);
    List<Users> getAllUsers();

    Users getUserByEmail(String email);

}
