package com.streamacho.meeting.room.service;

import com.streamacho.meeting.config.properties.ImageProperties;
import com.streamacho.meeting.image.model.dto.ImageDTO;
import com.streamacho.meeting.image.service.ImageService;
import com.streamacho.meeting.room.exception.RoomNotFoundException;
import com.streamacho.meeting.room.exception.ValidationException;
import com.streamacho.meeting.room.mapper.RoomMapper;
import com.streamacho.meeting.room.model.dto.RoomCreationDTO;
import com.streamacho.meeting.room.model.dto.RoomDTO;
import com.streamacho.meeting.room.model.entity.Room;
import com.streamacho.meeting.room.model.enumeration.RoomStatus;
import com.streamacho.meeting.room.repository.elasticsearch.RoomSearchRepository;
import com.streamacho.meeting.room.repository.jpa.RoomRepository;
import com.streamacho.meeting.room.validator.RoomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.streamacho.meeting.room.model.enumeration.RoomStatus.LIVE;
import static com.streamacho.meeting.room.model.enumeration.RoomStatus.PLANNED;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties({ImageProperties.class})
public class RoomService {

     private final ImageProperties imageProperties;
     private final ImageService imageService;
     private final RoomSearchRepository roomSearchRepository;
     private final RoomRepository roomRepository;
     private final RoomMapper roomMapper;

     public Page<RoomDTO> getRoomsDTO(String query, List<RoomStatus> statuses, Pageable pageable) {
          Page<Room> rooms = roomSearchRepository.fuzzySearchNonDeleted(query, statuses, pageable);
          return rooms.map(roomMapper::toRoomDTO);
     }

     public Page<RoomDTO> getRoomsDTO(List<RoomStatus> statuses, Pageable pageable) {
          Page<Room> rooms = roomSearchRepository.searchNonDeleted(statuses, pageable);
          return rooms.map(roomMapper::toRoomDTO);
     }

     public RoomDTO getRoomDTO(Long id) {
          final Room room = getRoomById(id);
          return roomMapper.toRoomDTO(room);
     }

     public RoomDTO createRoom(RoomCreationDTO roomCreationDTO, UserDetails issuer)
             throws IOException {
          final String logoUrl = createLogoUrl(roomCreationDTO.getLogoDTO());
          final Room room = roomMapper.toRoom(roomCreationDTO, issuer, logoUrl);
          final Room savedRoom = roomRepository.save(room);
          roomSearchRepository.save(savedRoom);
          return roomMapper.toRoomDTO(savedRoom);
     }

     private String createLogoUrl(ImageDTO imageDTO) throws IOException {
          return Objects.nonNull(imageDTO)
                  ? imageService.upload(imageDTO)
                  : imageProperties.getDefaultRoomLogoUrl();
     }

     public RoomDTO updateRoom(Long roomId, RoomCreationDTO roomCreationDTO, UserDetails issuer)
             throws IOException {
          final Room existingRoom = getRoomById(roomId);
          RoomValidator.of(existingRoom)
               .isModifiableBy(issuer)
               .ifInvalidThrow(ValidationException::of);
          final String logoUrl = createLogoUrl(roomCreationDTO.getLogoDTO());
          final Room updatedRoom = roomMapper.updateRoom(roomCreationDTO, logoUrl, existingRoom);
          final Room savedRoom = roomRepository.save(updatedRoom);
          roomSearchRepository.save(updatedRoom);
          return roomMapper.toRoomDTO(savedRoom);
     }

     public void deleteRoom(Long roomId, UserDetails issuer) {
          final Room room = getRoomById(roomId);
          RoomValidator.of(room)
               .isModifiableBy(issuer)
               .ifInvalidThrow(ValidationException::of);
          roomRepository.deleteSoft(room);
          roomSearchRepository.deleteSoft(room);
     }

     private Room getRoomById(Long id) {
          return roomRepository
               .findOneByIdAndDeletedFalse(id)
               .orElseThrow(RoomNotFoundException::of);
     }

     public Room getPlannedOrLiveRoomById(Long id) {
          return roomRepository
                  .findOneByIdAndDeletedFalseAndStatusIn(id, List.of(PLANNED, LIVE))
                  .orElseThrow(RoomNotFoundException::of);
     }
}
