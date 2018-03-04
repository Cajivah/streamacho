package com.streamacho.api.factory;

import com.streamacho.api.config.security.model.UserPrincipal;
import org.springframework.security.authentication.TestingAuthenticationToken;

public class AuthenticationFactory {

     public static TestingAuthenticationToken createAuthentication(String username) {
          return new TestingAuthenticationToken(createUserPrincipal(username), null);
     }

     public static UserPrincipal createUserPrincipal(String username) {
          return UserPrincipal.builder()
               .username(username)
               .build();
     }
}
