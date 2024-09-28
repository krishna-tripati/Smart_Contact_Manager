package com.scm.Repository;

import com.scm.Entity.Contact;
import com.scm.Entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepo extends JpaRepository<Contact,Integer>{

    //find the contact by user

    //custom finder method
    List<Contact> findByUser(Users user);  // also use Pageable here don't need to make extra only making for understanding

    // Custom query method
//    @Query("SELECT c FROM Contact c WHERE c.course.id= :userId")
//    List<Contact> findByUserId(@Param("userId") Integer userId);

    //for paginate
    Page<Contact> findByUser(Users user, Pageable pageable);


}
