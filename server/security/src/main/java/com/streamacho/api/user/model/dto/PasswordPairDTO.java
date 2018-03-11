package com.streamacho.api.user.model.dto;

import com.streamacho.api.util.annotation.password.Password;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordPairDTO {

     @Password
     private String password;
     private String matchingPassword;
}
