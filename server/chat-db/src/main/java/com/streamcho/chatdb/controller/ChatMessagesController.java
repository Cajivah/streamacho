package com.streamcho.chatdb.controller;

import com.streamcho.chatdb.dto.ChatMessageDto;
import com.streamcho.chatdb.service.ChatMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatMessagesController {

     private final ChatMessagesService chatMessagesService;

     @Autowired
     public ChatMessagesController(ChatMessagesService chatMessagesService) {
          this.chatMessagesService = chatMessagesService;
     }

     @GetMapping("/chat/{chatId}")
     public Page<ChatMessageDto> getLastChatMessages(@PathVariable String chatId, Pageable pageable) {
          return chatMessagesService.getChatMessages(chatId, pageable);
     }

}
