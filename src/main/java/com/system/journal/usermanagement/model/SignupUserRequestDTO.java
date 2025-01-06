package com.system.journal.usermanagement.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupUserRequestDTO {

    @NotBlank(message = "userName should not be blank")
    private String userName;
    @NotBlank(message = "email is mandatory")
    @Email
    private String email;
    private String password;
    private String firstName;
    private String lastName;

}
