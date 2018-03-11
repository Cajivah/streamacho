package com.streamacho.api.factory;

import com.streamacho.api.user.model.dto.ChangePasswordDTO;
import com.streamacho.api.user.model.dto.PasswordPairDTO;
import com.streamacho.api.user.model.entity.HashedPassword;
import com.streamacho.api.user.model.entity.UserCredentials;

public class UserCredentialsFactory {

     public static final String validChange = "sdhfapiIAFH352*&";
     public static final String differentChange = "asdagr9870BHJ$@";

     public static ChangePasswordDTO createChangePasswordDTO(String password) {
          return ChangePasswordDTO.builder()
               .currentPassword(password)
               .passwordPairDTO(createPasswordPainDTO(validChange))
               .build();
     }

     public static PasswordPairDTO createPasswordPainDTO(String password) {
          return PasswordPairDTO.builder()
               .password(password)
               .matchingPassword(password)
               .build();
     }

     public static HashedPassword createHashedPassword(String hashed) {
          return HashedPassword.ofHashed(hashed);
     }

     public static UserCredentials createUserCredentials(String username, HashedPassword password) {
          return UserCredentials.builder()
               .username(username)
               .password(password)
               .build();
     }
}
