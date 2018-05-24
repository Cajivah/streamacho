package com.streamacho.meeting.room.controller;

import com.streamacho.meeting.room.model.dto.RoomCreationDTO;
import com.streamacho.meeting.room.model.dto.RoomDTO;
import com.streamacho.meeting.room.model.enumeration.RoomStatus;
import com.streamacho.meeting.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

     private final RoomService roomService;

     @GetMapping("/now")
     public LocalDateTime now() {
          return LocalDateTime.now();
     }

     @PostMapping
     @ResponseStatus(HttpStatus.CREATED)
     public RoomDTO createRoom(@RequestBody @Valid RoomCreationDTO roomCreationDTO,
                               @AuthenticationPrincipal UserDetails issuer) throws IOException {
          return roomService.createRoom(roomCreationDTO, issuer);
     }

     @PutMapping("/{roomId}")
     @ResponseStatus(HttpStatus.OK)
     public RoomDTO updateRoom(@PathVariable Long roomId,
                               @RequestBody @Valid RoomCreationDTO roomCreationDTO,
                               @AuthenticationPrincipal UserDetails issuer) throws IOException {
          return roomService.updateRoom(roomId, roomCreationDTO, issuer);
     }

     @DeleteMapping("/{roomId}")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     public void deleteRoom(@PathVariable Long roomId,
                            @AuthenticationPrincipal UserDetails issuer) {
          roomService.deleteRoom(roomId, issuer);
     }

     @GetMapping
     public Page<RoomDTO> getRooms(@RequestParam(value = "status", required = false)
                                        List<RoomStatus> statuses,
                                   Pageable pageable) {
          statuses = Optional.ofNullable(statuses)
                             .orElse(RoomStatus.valuesAsList());
          return roomService.getRoomsDTO(statuses, pageable);
     }

     @GetMapping(params = "query")
     public Page<RoomDTO> getRooms(@RequestParam String query,
                                   @RequestParam(value = "status", required = false)
                                        List<RoomStatus> statuses,
                                   Pageable pageable) {
          statuses = Optional.ofNullable(statuses)
                             .orElse(RoomStatus.valuesAsList());
          return roomService.getRoomsDTO(query, statuses, pageable);
     }

     @GetMapping("/{id}")
     public RoomDTO getRoom(@PathVariable Long id) {
          return roomService.getRoomDTO(id);
     }
}
