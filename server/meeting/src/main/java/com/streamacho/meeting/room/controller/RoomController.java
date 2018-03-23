package com.streamacho.meeting.room.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
public class RoomController {

     @PostMapping
     @ResponseStatus(HttpStatus.CREATED)
     public Object createRoom(Authentication authentication) {
          return authentication;
     }
}
