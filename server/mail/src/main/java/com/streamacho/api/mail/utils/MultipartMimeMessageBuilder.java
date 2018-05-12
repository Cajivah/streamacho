package com.streamacho.api.mail.utils;

import lombok.Getter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MultipartMimeMessageBuilder {

     private static final boolean IS_HTML = true;
     private static final boolean IS_MULTIPART = true;

     @Getter
     private MimeMessage message;
     private MimeMessageHelper messageHelper;

     private MultipartMimeMessageBuilder(JavaMailSender javaMailSender) throws MessagingException {
          this.message = javaMailSender.createMimeMessage();
          this.messageHelper = new MimeMessageHelper(message, IS_MULTIPART);
     }

     public static MultipartMimeMessageBuilder of(JavaMailSender javaMailSender) throws MessagingException {
          return new MultipartMimeMessageBuilder(javaMailSender);
     }

     public MultipartMimeMessageBuilder to(String to) throws MessagingException {
          messageHelper.setTo(to);
          return this;
     }

     public MultipartMimeMessageBuilder to(InternetAddress to) throws MessagingException {
          messageHelper.setTo(to);
          return this;
     }

     public MultipartMimeMessageBuilder from(String from) throws MessagingException {
          messageHelper.setFrom(from);
          return this;
     }

     public MultipartMimeMessageBuilder subject(String subject) throws MessagingException {
          messageHelper.setSubject(subject);
          return this;
     }

     public MultipartMimeMessageBuilder htmlText(String text) throws MessagingException {
          messageHelper.setText(text, IS_HTML);
          return this;
     }
}
