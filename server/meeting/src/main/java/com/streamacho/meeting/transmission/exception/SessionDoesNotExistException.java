package com.streamacho.meeting.transmission.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Session for specified room does not exist")
public class SessionDoesNotExistException extends RuntimeException {
     private static final String MESSAGE = "Session for specified room does not exist";

     private SessionDoesNotExistException() {
          super(MESSAGE);
     }

     private SessionDoesNotExistException(Throwable t) {
          super(MESSAGE, t);
     }

     public static SessionDoesNotExistException of() {
          return new SessionDoesNotExistException();
     }

     public static SessionDoesNotExistException ofThrowable(Throwable throwable) {
          return new SessionDoesNotExistException(throwable);
     }
}