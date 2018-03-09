package com.streamacho.api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Could not update password")
public class PasswordUpdateException extends Fault {

     private PasswordUpdateException() {
          super();
     }

     private PasswordUpdateException(Throwable t) {
          super(t);
     }

     public static PasswordUpdateException of() {
          return new PasswordUpdateException();
     }

     public static PasswordUpdateException ofThrowable(Throwable t) {
          return new PasswordUpdateException(t);
     }
}
