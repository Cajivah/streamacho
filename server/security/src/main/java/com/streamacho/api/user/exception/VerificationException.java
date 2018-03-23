package com.streamacho.api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VerificationException extends RuntimeException {

     private static final String MESSAGE = "Could not process verification, invalid token";

     private VerificationException() {
          super(MESSAGE);
     }

     private VerificationException(Throwable t) {
          super(MESSAGE, t);
     }

     public static VerificationException of() {
          return new VerificationException();
     }

     public static VerificationException ofThrowable(Throwable t) {
          return new VerificationException(t);
     }
}
