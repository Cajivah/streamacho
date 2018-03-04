package com.streamacho.api.config.security.mapper;

import com.nimbusds.jwt.JWTClaimsSet;
import com.streamacho.api.config.security.model.UserPrincipal;
import com.streamacho.api.user.model.entity.UserCredentials;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

public abstract class SecurityMapper {

     public static UserPrincipal toUserPrincipal(JWTClaimsSet claims) {
          return UserPrincipal.builder()
               .username(claims.getSubject())
               .build();
     }

     public static UserDetails toUserDetails(UserCredentials userCredentials) {
          return User.builder()
               .username(userCredentials.getUsername())
               .password(userCredentials.getPassword().getValue())
               .authorities(Collections.emptyList())
               .build();
     }

     public static UserPrincipal toUserPrincipal(Authentication authentication) {
          return (UserPrincipal) authentication.getPrincipal();
     }
}
