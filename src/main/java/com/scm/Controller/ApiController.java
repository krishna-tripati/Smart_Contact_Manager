package com.scm.Controller;

import com.scm.Entity.Contact;
import com.scm.Services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api")
public class ApiController {
    //get contact
    @Autowired
    private ContactService contactService;

    @GetMapping("/contacts/{cId}")
    public String getContact(@PathVariable("cId") Integer cId, Model model){
        Contact getContact=contactService.getById(cId);
        model.addAttribute("contact", getContact);

        return "user/contact_details";
    }


}
