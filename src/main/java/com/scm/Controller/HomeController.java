package com.scm.Controller;

import com.scm.Entity.Users;
import com.scm.Services.UserService;
import com.scm.Forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String home(Model model){
        model.addAttribute("title","Home - Smart Contact Manager");
        return "home";
    }
    @RequestMapping("/about")
    public String about(Model model){
        return "about";
    }

//    @RequestMapping("/services")
//    public String service(Model model){
//        return "services";
//    }

    @GetMapping("/contact")
        public String contact(){
        return "contact";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/register")
    public String register(Model model){
        UserForm userForm=new UserForm();
       // userForm.setName("Krishna"); // sending default value
        model.addAttribute("userForm",userForm);
        return "register";
    }

    //processing register
    @PostMapping("/do-register")
    public String processregister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session){
        System.out.println("Processing registration");
        //fetch form data
        //userForm
        System.out.println(userForm);

        //validate form data
        // using annotation for validate form @Valid , BindingResult
        if(rBindingResult.hasErrors()){
            return "register";
        }

        //save to database
        //userservice
           //userForm--> User
        Users user=new Users();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);
 //default profile save  // user.setProfilePic("https://www.shutterstock.com/shutterstock/photos/2231500721/display_1500/stock-photo-chatbot-conversation-assistant-person-using-online-customer-service-with-chat-bot-to-get-support-2231500721.jpg");

        Users savedUser= userService.saveUser(user);
        System.out.println("user saved");


        //message: "Registration successful"
          // add the message
        Message message= Message.builder().content("Registration Successful").type(MessageType.green).build();
        session.setAttribute("message",message);


        //redirect login page
        return "redirect:/register";
    }



}
