package com.streamacho.meeting.room.service;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.streamacho.meeting.room.model.enumeration.RoomStatus.LIVE;
import static com.streamacho.meeting.room.model.enumeration.RoomStatus.PLANNED;

@Service
@RequiredArgsConstructor
public class RoomService {

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

     public RoomDTO createRoom(RoomCreationDTO roomCreationDTO, UserDetails issuer) {
          final Room room = roomMapper.toRoom(roomCreationDTO, issuer);
          final Room savedRoom = roomRepository.save(room);
          roomSearchRepository.save(savedRoom);
          return roomMapper.toRoomDTO(savedRoom);
     }

     public RoomDTO updateRoom(Long roomId, RoomCreationDTO roomCreationDTO, UserDetails issuer) {
          final Room existingRoom = getRoomById(roomId);
          RoomValidator.of(existingRoom)
               .isModifiableBy(issuer)
               .ifInvalidThrow(ValidationException::of);
          final Room updatedRoom = roomMapper.updateRoom(roomCreationDTO, existingRoom);
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
