package com.streamacho.api.mail.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MailSendingException extends RuntimeException {

     private static final String DEFAULT_MSG = "Could not send mail";

     private MailSendingException() {
          super(DEFAULT_MSG);
     }

     private MailSendingException(Throwable t) {
          super(DEFAULT_MSG, t);
     }

     public static MailSendingException of() {
          return new MailSendingException();
     }

     public static MailSendingException ofThrowable(Throwable t) {
          return new MailSendingException(t);
     }
}
