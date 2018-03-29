package com.streamacho.meeting.room.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {
     private static final String MESSAGE = "Validation failure";

     private ValidationException() {
          super(MESSAGE);
     }

     private ValidationException(Throwable t) {
          super(MESSAGE, t);
     }

     public static ValidationException of() {
          return new ValidationException();
     }

     public static ValidationException ofThrowable(Throwable t) {
          return new ValidationException(t);
     }
}
