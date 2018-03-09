package com.streamacho.api.config.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid credentials")
public class LoginFailedException extends BadCredentialsException {

     private static final String DEFAULT_MSG = "Invalid credentials, could not log in";

     private LoginFailedException() {
          super(DEFAULT_MSG);
     }

     private LoginFailedException(Throwable t) {
          super(DEFAULT_MSG, t);
     }

     public static LoginFailedException of() {
          return new LoginFailedException();
     }

     public static LoginFailedException ofThrowable(Throwable t) {
          return new LoginFailedException(t);
     }
}
