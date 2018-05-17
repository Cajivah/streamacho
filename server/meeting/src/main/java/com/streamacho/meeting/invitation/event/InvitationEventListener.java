package com.streamacho.meeting.invitation.event;

import com.streamacho.meeting.mail.service.MailService;
import com.streamacho.meeting.room.mapper.RoomInvitationMapper;
import com.streamacho.meeting.room.model.dto.RoomInvitationMailDTO;
import com.streamacho.meeting.room.model.event.OnInvitedToRoomEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InvitationEventListener {

     private final MailService mailService;
     private final RoomInvitationMapper roomInvitationMapper;

     @Async
     @EventListener
     public void handleUserInvitedToRoom(OnInvitedToRoomEvent event) {
          RoomInvitationMailDTO invitationDTO = roomInvitationMapper.toRoomInvitationDTO(event);
          mailService.sendRoomInvitationEmail(invitationDTO);
     }
}
