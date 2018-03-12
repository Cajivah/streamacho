package com.streamacho.api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Could not update password")
public class PasswordsMatchException extends Fault {

     private PasswordsMatchException() {
          super();
     }

     private PasswordsMatchException(Throwable t) {
          super(t);
     }

     public static PasswordsMatchException of() {
          return new PasswordsMatchException();
     }

     public static PasswordsMatchException ofThrowable(Throwable t) {
          return new PasswordsMatchException(t);
     }
}
