package com.streamacho.chatdb.controller;

import com.streamacho.chatdb.dto.ChatMessageDto;
import com.streamacho.chatdb.service.ChatMessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatMessagesController {

     private final ChatMessagesService chatMessagesService;

     @GetMapping("/chat/{chatId}")
     public Page<ChatMessageDto> getLastChatMessages(@PathVariable Long chatId, Pageable pageable) {
          return chatMessagesService.getChatMessages(chatId, pageable);
     }

}
