package com.streamacho.api.config.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidJWTException extends BadCredentialsException {

     private static final String DEFAULT_MSG = "Invalid JWT token!";

     public InvalidJWTException() {
          super(DEFAULT_MSG);
     }

     public InvalidJWTException(Throwable t) {
          super(DEFAULT_MSG, t);
     }

     public static InvalidJWTException of() {
          return new InvalidJWTException();
     }

     public static InvalidJWTException ofThrowable(Throwable t) {
          return new InvalidJWTException(t);
     }
}
