package com.streamacho.meeting.room.mapper;

import com.streamacho.meeting.room.model.dto.RoomCreationDTO;
import com.streamacho.meeting.room.model.dto.RoomDTO;
import com.streamacho.meeting.room.model.entity.Room;
import com.streamacho.meeting.util.date.mapper.DateMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Mapper(componentModel = "spring", uses = DateMapper.class)
public interface RoomMapper {

     @Mapping(target = "startAt", source = "startAtDate")
     RoomDTO toRoomDTO(Room room);

     Collection<RoomDTO> toRoomDTOs(Collection<Room> rooms);

     @Mapping(target = "id", ignore = true)
     @Mapping(target = "deleted", ignore = true)
     @Mapping(target = "createdDate", ignore = true)
     @Mapping(target = "modifiedDate", ignore = true)
     @Mapping(target = "status", ignore = true)
     @Mapping(target = "transmissionStartedAt", ignore = true)
     @Mapping(target = "organiser", source = "user.username")
     @Mapping(target = "name", source = "room.name")
     @Mapping(target = "description", source = "room.description")
     @Mapping(target = "startAtDate", source = "room.startAt")
     @Mapping(target = "tags", source = "room.tags")
     Room toRoom(RoomCreationDTO room, UserDetails user, String logoUrl);

     @Mapping(target = "id", ignore = true)
     @Mapping(target = "organiser", ignore = true)
     @Mapping(target = "deleted", ignore = true)
     @Mapping(target = "createdDate", ignore = true)
     @Mapping(target = "modifiedDate", ignore = true)
     @Mapping(target = "status", ignore = true)
     @Mapping(target = "transmissionStartedAt", ignore = true)
     @Mapping(target = "startAtDate", source = "startAt")
     Room updateRoom(RoomCreationDTO roomCreationDTO, String logoUrl, @MappingTarget Room room);
}
