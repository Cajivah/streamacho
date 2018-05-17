package com.streamacho.meeting.mail.service;

import com.streamacho.api.mail.exception.MailSendingException;
import com.streamacho.meeting.mail.factory.RoomInvitationFactory;
import com.streamacho.meeting.room.model.dto.RoomInvitationMailDTO;
import io.vavr.CheckedConsumer;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

     private final RoomInvitationFactory roomInvitationFactory;
     private final JavaMailSender javaMailSender;

     public void sendRoomInvitationEmail(RoomInvitationMailDTO mailDTO) {
         Try.of(() -> roomInvitationFactory.createMessage(mailDTO))
                . andThenTry((CheckedConsumer<? super MimeMessage>) javaMailSender::send)
                 .getOrElseThrow(MailSendingException::ofThrowable);
     }
}
