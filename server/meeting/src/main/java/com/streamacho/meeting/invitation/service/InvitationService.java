package com.streamacho.meeting.invitation.service;

import com.streamacho.meeting.invitation.model.dto.InvitationDTO;
import com.streamacho.meeting.invitation.validator.InvitationValidator;
import com.streamacho.meeting.room.model.entity.Room;
import com.streamacho.meeting.room.model.event.OnInvitedToRoomEvent;
import com.streamacho.meeting.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.validation.ValidationException;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class InvitationService {

     private final ApplicationEventPublisher eventPublisher;
     private final RoomService roomService;

     public void inviteUser(Long roomId, UserDetails issuer, InvitationDTO invitationDTO,
                            Locale locale) {
          InternetAddress recipientEmail = invitationDTO.getEmail();
          String recipientUsername = invitationDTO.getUsername();
          Room room = roomService.getPlannedOrLiveRoomById(roomId);
          InvitationValidator.of(room)
                             .canIvite(issuer)
                             .ifInvalidThrow(ValidationException::new);

          eventPublisher.publishEvent(new OnInvitedToRoomEvent(this, issuer.getUsername(),
                  recipientEmail, recipientUsername, room, locale));
     }

}
