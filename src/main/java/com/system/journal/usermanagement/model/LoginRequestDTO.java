package com.system.journal.usermanagement.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "userName is mandatory")
    private String userName;
    @NotBlank(message = "password should not empty or null")
    private String password;
}
