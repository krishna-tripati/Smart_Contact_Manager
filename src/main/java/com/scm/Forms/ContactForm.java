package com.scm.Forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForm {

    @NotBlank(message = "Name is Required") //form validation
    private String name;

    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email Address")
    private String email;
    @NotBlank(message = "Phone Number is Required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    private String phoneNumber;
    @NotBlank(message = "Contact Address is Required")
    private String address;
    private String description;
    private boolean favourite=false;

    //create annotation for file validation
    //size
    //resolution
    //type
    private MultipartFile contactImage;

    private String picture; // for update contact Image

}
