package com.scm.Controller;

import com.scm.Entity.Contact;
import com.scm.Entity.Users;
import com.scm.Forms.ContactForm;
import com.scm.Services.ContactService;
import com.scm.Services.ImageService;
import com.scm.Services.UserService;
import com.scm.helpers.AppConstant;
import com.scm.helpers.LoggedInUser;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;

    private Logger logger= LoggerFactory.getLogger(ContactController.class);

    //add contact page
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addContact(Model model){
        ContactForm contactForm =new ContactForm();
        contactForm.setFavourite(true);
        model.addAttribute("contactForm",contactForm);
        return "user/add_contact";
    }

    //add contact processing
    @RequestMapping(value = "/add", method = RequestMethod.POST)                      //Authentication for accessing user email
    public String saveContact(@Valid  @ModelAttribute ContactForm contactForm , BindingResult result,
         Authentication authentication, HttpSession session) throws IOException {
        //processing add contact
        System.out.println("Processing add contact");
        //fetch form data from contactform
        System.out.println("before contact save database"+contactForm); // print contactform

        //Validation form
        if(result.hasErrors()){
            result.getAllErrors().forEach(error-> logger.info(error.toString()));

            //error message show
            Message message= Message.builder().content("Please correct the following errors").type(MessageType.red).build();
            session.setAttribute("message",message);
            return "user/add_contact";
        }

        // accessing username from authentication // set to the contact user database
         String username= LoggedInUser.getEmailOfLoggedInUser(authentication);
         Users user= userService.getUserByEmail(username);
         System.out.println("user name: "+user);

         //process the contact picture
           //image process
      //image upload or not in console (testing purpose)  logger.info("file information: {}",contactForm.getContactImage().getOriginalFilename());

        //->image upload code
        String filename= UUID.randomUUID().toString();

        String fileURL=imageService.uploadImage(contactForm.getContactImage(),filename);

        //save to database
           //ContactForm to Contact
        Contact contact=new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setAddress(contactForm.getAddress());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setDescription(contactForm.getDescription());
        contact.setFavourite(contactForm.isFavourite());
        contact.setUser(user); //set user
         //set contact picture url and imageid
        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(filename);

        Contact saveContact= contactService.save(contact);
        System.out.println("contact saved");
        System.out.println("when contact saved"+contactForm);

        //message: "Saved Contact successful"
            // add the message
        Message message= Message.builder().content(" Saved Contact Successful").type(MessageType.green).build();
        session.setAttribute("message",message);

        //redirect add contact page
        return  "redirect:/user/contacts/add";
    }

    //view contact page
    @RequestMapping
    public String viewcontact(
            @RequestParam(value ="page", defaultValue="0") int page, // for pages
            @RequestParam(value ="size",  defaultValue= AppConstant.PAGE_SIZE+"") int size,
            @RequestParam(value ="sortBy",  defaultValue="name") String sortBy,
            @RequestParam(value ="direction" , defaultValue="asc") String direction
            ,Model model, Authentication authentication){

        //load all the user contacts
        String username= LoggedInUser.getEmailOfLoggedInUser(authentication);
        Users user = userService.getUserByEmail(username);
        List<Contact>contacts=contactService.getByUser(user); //getting contact by user
        Page<Contact> pageContact= contactService.getByUser(user,page,size,sortBy,direction); //get pagess
        model.addAttribute("contacts",contacts);
        model.addAttribute("pageContact",pageContact);
        model.addAttribute("pageSize", AppConstant.PAGE_SIZE);


        return "user/viewcontacts";
    }


   // delete contact
    @RequestMapping("/delete/{cId}")
    public String deleteContact(@PathVariable("cId") Integer cId, HttpSession session){
        contactService.delete(cId);
        logger.info("cId {} deleted",cId);

        //message: "Deleted successfully"
        // add the message
        Message message= Message.builder().content("Deleted Successfully").type(MessageType.blue).build();
        session.setAttribute("message",message);

        return "redirect:/user/viewcontacts";
    }

    // upadate contact view page
    @GetMapping("/view/{cId}")
    public String updateContact(@PathVariable("cId") Integer cId, Model model){
        var contact=contactService.getById(cId);

        ContactForm contactForm=new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavourite(contact.isFavourite());
        contactForm.setPicture(contact.getPicture());

        model.addAttribute("contactForm",contactForm);
        model.addAttribute("cId",cId);


        return "user/update_contact";

    }

    // update contact process
    @RequestMapping(value = "/update/{cId}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("cId") Integer cId,
      @Valid @ModelAttribute ContactForm contactForm, BindingResult result ,Model model, HttpSession session) throws IOException {

        // any error return to update contact page
        if(result.hasErrors()){
            return "user/update_contact";
        }

        //update the contact
        var con=new Contact();
        con.setCId(cId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavourite(contactForm.isFavourite());
        //con.setPicture(contactForm.getPicture());

        //process image
        if(contactForm.getContactImage() !=null && !contactForm.getContactImage().isEmpty()) {
            String fileName = UUID.randomUUID().toString();
            String imageURL = imageService.uploadImage(contactForm.getContactImage(), fileName);
            con.setCloudinaryImagePublicId(fileName);
            con.setPicture(imageURL);
            contactForm.setPicture(imageURL);
        }else {
            System.out.println("file is empty");
        }

        var updatedContact= contactService.update(con);
        logger.info("updated contact {}", updatedContact);

        //message: "Contact Updated successfully"
        // add the message
        Message message= Message.builder().content("Contact Updated Successfully")
                .type(MessageType.green).build();
        session.setAttribute("message",message);
        return "redirect:/user/contacts/view/" +cId;
    }


}
