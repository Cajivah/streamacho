package com.streamacho.meeting.room.controller;

import com.streamacho.meeting.room.model.dto.RoomCreationDTO;
import com.streamacho.meeting.room.model.dto.RoomDTO;
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

import java.time.LocalDateTime;

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
     public RoomDTO createRoom(@RequestBody RoomCreationDTO roomCreationDTO,
                               @AuthenticationPrincipal UserDetails issuer) {
          return roomService.createRoom(roomCreationDTO, issuer);
     }

     @PutMapping("/{roomId}")
     @ResponseStatus(HttpStatus.OK)
     public RoomDTO updateRoom(@PathVariable Long roomId,
                               @RequestBody RoomCreationDTO roomCreationDTO,
                               @AuthenticationPrincipal UserDetails issuer) {
          return roomService.updateRoom(roomId, roomCreationDTO, issuer);
     }

     @DeleteMapping("/{roomId}")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     public void deleteRoom(@PathVariable Long roomId,
                            @AuthenticationPrincipal UserDetails issuer) {
          roomService.deleteRoom(roomId, issuer);
     }

     @GetMapping
     public Page<RoomDTO> getRooms(Pageable pageable) {
          return roomService.getRoomsDTO(pageable);
     }

     @GetMapping(params = "query")
     public Page<RoomDTO> fullTextSearch(@RequestParam String query,
                                         Pageable pageable) {
          return roomService.fullTextSearch(query, pageable);
     }

     @GetMapping("/{id}")
     public RoomDTO getRoom(@PathVariable Long id) {
          return roomService.getRoomDTO(id);
     }
}
