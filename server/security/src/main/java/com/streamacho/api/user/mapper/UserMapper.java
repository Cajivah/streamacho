package com.streamacho.api.user.mapper;

import com.streamacho.api.user.model.dto.ChangePasswordDTO;
import com.streamacho.api.user.model.dto.PasswordPairDTO;
import com.streamacho.api.user.model.dto.UserDetailsDTO;
import com.streamacho.api.user.model.dto.UserRegistrationDTO;
import com.streamacho.api.user.model.entity.HashedPassword;
import com.streamacho.api.user.model.entity.UserCredentials;
import com.streamacho.api.util.mapper.DateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public abstract class UserMapper {

     PasswordEncoder passwordEncoder;

     @Autowired
     public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
          this.passwordEncoder = passwordEncoder;
     }

     @Mapping(target = "password", source = "passwordPairDTO")
     @Mapping(target = "id", ignore = true)
     @Mapping(target = "created", ignore = true)
     @Mapping(target = "modified", ignore = true)
     public abstract UserCredentials toUser(UserRegistrationDTO userRegistrationDTO);

     public abstract UserDetailsDTO toUserDetailsDTO(UserCredentials userCredentials);

     @Mapping(target = "id", ignore = true)
     @Mapping(target = "username", ignore = true)
     @Mapping(target = "email", ignore = true)
     @Mapping(target = "created", ignore = true)
     @Mapping(target = "modified", ignore = true)
     @Mapping(target = "logins", ignore = true)
     public abstract UserCredentials changePassword(PasswordPairDTO passwordPairDTO,
                                                    @MappingTarget UserCredentials user);

     public UserCredentials changePassword(ChangePasswordDTO changePasswordDTO,
                                           @MappingTarget UserCredentials user) {
          return changePassword(changePasswordDTO.getPasswordPairDTO(), user);
     }

     public HashedPassword toHashedPassword(PasswordPairDTO passwordPairDTO) {
          return toHashedPassword(passwordPairDTO.getPassword());
     }

     public HashedPassword toHashedPassword(String password) {
          return HashedPassword.of(password, passwordEncoder);
     }
}