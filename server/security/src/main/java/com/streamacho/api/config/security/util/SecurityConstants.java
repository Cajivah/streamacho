package com.streamacho.api.config.security.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

     public static final String REGISTRATION_URL = "/registration";
     public static final String VERIFICATION_URL = "/verification";
     public static final String LOGOUT_URL = "/logout";
     public static final String[] AUTH_WHITELIST = {
          // -- swagger ui
          "/v2/api-docs",
          "/swagger-resources",
          "/swagger-resources/**",
          "/configuration/ui",
          "/configuration/security",
          "/swagger-ui.html",
          "/webjars/**",
     };
}
