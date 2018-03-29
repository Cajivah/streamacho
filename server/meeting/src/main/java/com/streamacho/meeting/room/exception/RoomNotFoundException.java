package com.streamacho.meeting.room.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Could not find room specified")
public class RoomNotFoundException extends RuntimeException {
     private static final String MESSAGE = "Could not find room specified";

     private RoomNotFoundException() {
          super(MESSAGE);
     }

     private RoomNotFoundException(Throwable t) {
          super(MESSAGE, t);
     }

     public static RoomNotFoundException of() {
          return new RoomNotFoundException();
     }

     public static RoomNotFoundException ofThrowable(Throwable t) {
          return new RoomNotFoundException(t);
     }
}