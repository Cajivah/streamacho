package com.streamacho.api.user.model.dto;

import com.streamacho.api.util.annotation.matchingPasswords.MatchingPasswords;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRegistrationDTO {

     @NotBlank
     private String username;
     @Email
     @NotBlank
     private String email;
     @MatchingPasswords
     private PasswordPairDTO passwordPairDTO;
}
