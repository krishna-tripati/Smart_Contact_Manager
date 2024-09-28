package com.scm.Forms;

import jakarta.validation.constraints.*;
import lombok.*;

// lombok
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm { //user send the data in userform

    //Form Validation
@NotBlank(message = "User Name is Required")
@Size(min = 3, message = "Minimum 3 Character is required")
private String name;

@NotBlank(message = "Email is Required")
@Email(message = "Invalid Email Address")
private String email;

@NotBlank(message = "Password is Required")
@Size(min = 4, message = "Minimum 4 Character is Required")
private String password;

private String about;

@Size(min = 4, max = 10, message = "Invalid Phone Number")
private String phoneNumber;

}
