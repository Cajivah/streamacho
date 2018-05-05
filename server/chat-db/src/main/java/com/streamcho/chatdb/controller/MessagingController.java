package com.streamcho.chatdb.controller;

import com.streamcho.chatdb.dto.SystemChatMessagePayload;
import com.streamcho.chatdb.service.ChatMessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessagingController {

     private final ChatMessagesService chatMessagesService;

     @Autowired
     public MessagingController(ChatMessagesService chatMessagesService) {
          this.chatMessagesService = chatMessagesService;
     }

     @StreamListener(Processor.INPUT)
     @SendTo(Processor.OUTPUT)
     public SystemChatMessagePayload handleMessage(SystemChatMessagePayload payload) {
          chatMessagesService.saveMessage(payload);
          return payload;
     }
}
