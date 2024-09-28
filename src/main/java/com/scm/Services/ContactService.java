package com.scm.Services;

import com.scm.Entity.Contact;
import com.scm.Entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {
   //save contact
    Contact save(Contact contact);

    //update contact
    Contact update(Contact contact);

    //get contact
    List<Contact> getAll();

    //get contact by id
    Contact getById(Integer id);

    //delete contact
    void delete(Integer id);

    //search contact
    List<Contact> search(String name, String email, String phoneNumber);

    //get contact by user id
//   List<Contact> getByUserId(Integer id);

 List<Contact> getByUser(Users user);


 //pagination
 Page<Contact> getByUser(Users user, int page, int size, String sortField, String sortDirection);

}
