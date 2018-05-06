package com.streamacho.chat.controller;

import com.streamacho.chat.dto.OnlineUserDto;
import com.streamacho.chat.service.ChatRoomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ChatRoomController {

     private final ChatRoomService chatRoomService;

     @GetMapping("/chat/{chatId}/online")
     public Page<OnlineUserDto> getOnlineUsers(@PathVariable Long chatId, Pageable pageable) {
          List<OnlineUserDto> onlineUsers = chatRoomService.getOnlineUsers(chatId);
          return new PageImpl<>(onlineUsers, pageable, onlineUsers.size());
     }
}
