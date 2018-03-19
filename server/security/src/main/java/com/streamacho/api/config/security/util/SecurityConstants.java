package com.streamacho.api.config.security.util;

import com.nimbusds.jose.JWSAlgorithm;

public class SecurityConstants {

     public static final String SECRET_64_BYTE = "J@McQfTjWnZr4u7x!A%D*G-KaPdRgUkXp2s5v8y/B?E(H+MbQeThVmYq3t6w9z$C";
     public static final byte[] SECRET_BYTES = SECRET_64_BYTE.getBytes();
     public static final long EXPIRATION_TIME = 864_000_000;
     public static final String TOKEN_PREFIX = "Bearer ";
     public static final String AUTHORITIES_CLAIM_KEY = "authorities";
     public static final JWSAlgorithm ALGORITHM = JWSAlgorithm.HS512;
     public static final String REGISTRATION_URL = "/users";
     public static final String VERIFICATION_URL = "/verification";
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
