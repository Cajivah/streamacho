package com.streamacho.meeting.room.mapper;

import com.streamacho.meeting.room.model.dto.RoomCreationDTO;
import com.streamacho.meeting.room.model.dto.RoomDTO;
import com.streamacho.meeting.room.model.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface RoomMapper {

     RoomDTO toRoomDTO(Room room);

     Collection<RoomDTO> toRoomDTOs(Collection<Room> rooms);

     @Mapping(target = "id", ignore = true)
     @Mapping(target = "closed", ignore = true)
     @Mapping(target = "deleted", ignore = true)
     @Mapping(target = "created", ignore = true)
     @Mapping(target = "modified", ignore = true)
     @Mapping(target = "organiser", source = "user.username")
     @Mapping(target = "name", source = "room.name")
     @Mapping(target = "description", source = "room.description")
     @Mapping(target = "startAt", source = "room.startAt")
     @Mapping(target = "tags", source = "room.tags")
     Room toRoom(RoomCreationDTO room, UserDetails user);

     @Mapping(target = "id", ignore = true)
     @Mapping(target = "organiser", ignore = true)
     @Mapping(target = "closed", ignore = true)
     @Mapping(target = "deleted", ignore = true)
     @Mapping(target = "created", ignore = true)
     @Mapping(target = "modified", ignore = true)
     Room updateRoom(RoomCreationDTO roomCreationDTO, @MappingTarget Room room);
}
