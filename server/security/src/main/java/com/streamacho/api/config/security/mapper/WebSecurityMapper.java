package com.streamacho.api.config.security.mapper;

import com.streamacho.api.user.model.entity.UserCredentials;
import org.mapstruct.Mapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

@Mapper(componentModel = "spring")
public interface WebSecurityMapper {

     default UserDetails toUserDetails(UserCredentials userCredentials) {
          return User.builder()
               .username(userCredentials.getUsername())
               .password(userCredentials.getPassword().getValue())
               .authorities(Collections.emptyList())
               .build();
     }

     default User toPrincipal(Authentication authentication) {
          return (User) authentication.getPrincipal();
     }
}
