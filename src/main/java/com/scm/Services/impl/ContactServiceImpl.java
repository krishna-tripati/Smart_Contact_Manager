package com.scm.Services.impl;

import com.scm.Entity.Contact;
import com.scm.Entity.Users;
import com.scm.Repository.ContactRepo;
import com.scm.Services.ContactService;
import com.scm.helpers.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
        return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
          //update contact // --> accessing contact old data
      var contactOldId= contactRepo.findById(contact.getCId()).orElseThrow(()->
              new ResourceNotFoundException("Contact Not Found"));

      contactOldId.setName(contact.getName());
      contactOldId.setEmail(contact.getEmail());
      contactOldId.setPhoneNumber(contact.getPhoneNumber());
      contactOldId.setAddress(contact.getAddress());
      contactOldId.setDescription(contact.getDescription());
      contactOldId.setPicture(contact.getPicture());
      contactOldId.setFavourite(contact.isFavourite());
      contactOldId.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());

      return contactRepo.save(contactOldId);
  }


    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(Integer id) {
        return contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Contact Not Found"));
    }

    @Override
    public void delete(Integer id) {
       var contact= contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException
                ("Contact Not Found"));

         contactRepo.delete(contact);

    }

    @Override
    public List<Contact> search(String name, String email, String phoneNumber) {
        return null;
    }

    @Override
    public List<Contact> getByUser(Users user) {
        return contactRepo.findByUser(user);
    }


//   @Override
//    public List<Contact> getByUserId(Integer id) {
//        return contactRepo.findByUserId(id);
//    }


   // Paginate
    @Override
    public Page<Contact> getByUser(Users user, int page, int size,
                                   String sortBy, String direction) {
       Sort sort=direction.equals("desc")? Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable= PageRequest.of(page, size, sort);
        return contactRepo.findByUser(user,pageable);
    }

}
