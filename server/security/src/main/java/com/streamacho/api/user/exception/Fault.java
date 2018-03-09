package com.streamacho.api.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error")
public class Fault extends RuntimeException {

     public Fault() {
          super();
     }

     public Fault(Throwable t) {
          super(t);
     }
}
