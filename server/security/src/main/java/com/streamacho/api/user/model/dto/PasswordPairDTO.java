package com.streamacho.api.user.model.dto;

import com.streamacho.api.util.annotation.password.Password;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordPairDTO {

     @Password
     private String password;
     private String matchingPassword;
}
