package com.streamacho.api.config.security.mapper;

import com.nimbusds.jwt.JWTClaimsSet;
import com.streamacho.api.user.model.entity.UserCredentials;
import org.mapstruct.Mapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

@Mapper(componentModel = "spring")
public abstract class WebSecurityMapper {

     public UserDetails toUserDetails(UserCredentials userCredentials) {
          return User.builder()
               .username(userCredentials.getUsername())
               .password(userCredentials.getPassword().getValue())
               .authorities(Collections.emptyList())
               .build();
     }

     public UserDetails toUser(JWTClaimsSet claims) {
          return User.builder()
               .username(claims.getSubject())
               .build();
     }

     public User toPrincipal(Authentication authentication) {
          return (User) authentication.getPrincipal();
     }
}
