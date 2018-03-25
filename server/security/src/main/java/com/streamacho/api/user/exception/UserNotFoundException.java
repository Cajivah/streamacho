package com.streamacho.api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends Fault {

     private static final String MESSAGE = "Could not find specified user";

     private UserNotFoundException() {
          super(MESSAGE);
     }

     private UserNotFoundException(Throwable t) {
          super(MESSAGE, t);
     }

     public static UserNotFoundException of() {
          return new UserNotFoundException();
     }

     public static UserNotFoundException ofThrowable(Throwable t) {
          return new UserNotFoundException(t);
     }
}
