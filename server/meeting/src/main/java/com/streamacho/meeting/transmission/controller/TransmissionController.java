package com.streamacho.meeting.transmission.controller;

import com.streamacho.meeting.transmission.model.dto.SessionDTO;
import com.streamacho.meeting.transmission.service.TransmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class TransmissionController {

     private final TransmissionService transmissionService;

     @PostMapping("/{roomId}/sessions")
     public SessionDTO create(@PathVariable Long roomId,
                              @AuthenticationPrincipal UserDetails issuer) {
          return transmissionService.startStream(roomId, issuer);
     }

     @PostMapping("/{roomId}/join")
     public SessionDTO join(@PathVariable Long roomId,
                            @AuthenticationPrincipal UserDetails issuer) {
          return transmissionService.joinStream(roomId, issuer);
     }

     @DeleteMapping("/{roomId}/sessions")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     public void close(@PathVariable Long roomId,
                       @AuthenticationPrincipal UserDetails issuer) {
          transmissionService.closeStream(roomId, issuer);
     }
}
