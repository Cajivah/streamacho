package com.streamacho.meeting.room.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Builder
@Data
public class RoomsDTO {
     private Collection<RoomDTO> rooms;
}
