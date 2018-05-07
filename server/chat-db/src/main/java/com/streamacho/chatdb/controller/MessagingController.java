package com.streamacho.chatdb.controller;

import com.streamacho.chatdb.dto.SystemChatMessagePayload;
import com.streamacho.chatdb.service.ChatMessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessagingController {

     private final ChatMessagesService chatMessagesService;

     @StreamListener(Processor.INPUT)
     @SendTo(Processor.OUTPUT)
     public SystemChatMessagePayload handleMessage(SystemChatMessagePayload payload) {
          chatMessagesService.saveMessage(payload);
          return payload;
     }
}
