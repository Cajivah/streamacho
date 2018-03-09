package com.streamacho.api.config.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class JWTCreationException extends BadCredentialsException {

     private static final String DEFAULT_MSG = "Could not create JWT token";

     private JWTCreationException() {
          super(DEFAULT_MSG);
     }

     private JWTCreationException(Throwable t) {
          super(DEFAULT_MSG, t);
     }

     public static JWTCreationException of() {
          return new JWTCreationException();
     }

     public static JWTCreationException ofThrowable(Throwable t) {
          return new JWTCreationException(t);
     }
}