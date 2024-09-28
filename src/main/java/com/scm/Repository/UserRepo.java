package com.scm.Repository;

import com.scm.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {


    //custom finder
    Optional<Users> findByEmail(String email);
}
