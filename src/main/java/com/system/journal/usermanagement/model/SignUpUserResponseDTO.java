package com.system.journal.usermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpUserResponseDTO {
    private Long Id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
}
