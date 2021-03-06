package com.streamacho.api.mail.service;

import com.streamacho.api.mail.exception.MailSendingException;
import com.streamacho.api.mail.factory.PasswordResetFactory;
import com.streamacho.api.mail.factory.VerifyRegistrationFactory;
import com.streamacho.api.user.model.dto.PasswordResetMailDTO;
import com.streamacho.api.user.model.dto.VerificationMailDTO;
import io.vavr.CheckedConsumer;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MailService {

     private final JavaMailSender javaMailSender;
     private final PasswordResetFactory passwordResetFactory;
     private final VerifyRegistrationFactory verifyRegistrationFactory;

     public void sendResetPasswordMail(PasswordResetMailDTO mailDTO) {
          Try.of(() -> passwordResetFactory.createMessage(mailDTO))
             .andThenTry((CheckedConsumer<? super MimeMessage>) javaMailSender::send)
             .getOrElseThrow(MailSendingException::ofThrowable);
     }

     public void sendVerificationMail(VerificationMailDTO mailDTO) {
          Try.of(() -> verifyRegistrationFactory.createMessage(mailDTO))
               .andThenTry((CheckedConsumer<? super MimeMessage>) javaMailSender::send)
               .getOrElseThrow(MailSendingException::ofThrowable);
     }
}
