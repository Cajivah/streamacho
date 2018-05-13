package com.streamacho.meeting.room.mapper;

import com.streamacho.meeting.room.model.dto.RoomInvitationMailDTO;
import com.streamacho.meeting.room.model.event.OnInvitedToRoomEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomInvitationMapper {

     @Mapping(target = "organiserUsername", source = "issuerUsername")
     @Mapping(target = "recipientAddress", source = "invitedEmail")
     @Mapping(target = "recipientUsername", source = "invitedUsername")
     @Mapping(target = "roomId", source = "room.id")
     @Mapping(target = "roomName", source = "room.name")
     RoomInvitationMailDTO toRoomInvitationDTO(OnInvitedToRoomEvent event);
}
