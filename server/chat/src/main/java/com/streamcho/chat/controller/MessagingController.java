package com.streamcho.chat.controller;

import com.streamcho.chat.dto.SystemMessagePayload;
import com.streamcho.chat.dto.UserChatMessagePayload;
import com.streamcho.chat.service.ChatMessagingService;
import com.streamcho.chat.service.SystemMessagesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Log4j2
@Controller
public class MessagingController {

     private final SystemMessagesService systemMessagesService;

     private final ChatMessagingService chatMessagingService;

     @Autowired
     public MessagingController(SystemMessagesService systemMessagesService, ChatMessagingService chatMessagingService) {
          this.systemMessagesService = systemMessagesService;
          this.chatMessagingService = chatMessagingService;
     }

     @MessageMapping("${streamcho.chat.chatSendPrefix}/{chatId}")
     public void receiveChatMessage(@Payload String message, @DestinationVariable Long chatId) {
          log.debug(String.format("Received text for chat %s with text %s", chatId, message));
          UserChatMessagePayload inputMessagePayload = UserChatMessagePayload.builder()
               .chatId(chatId)
               .text(message)
               .username("robert")
               .build();

          systemMessagesService.sendChatMessageToSystem(inputMessagePayload);
     }

     @StreamListener(Sink.INPUT)
     public void sendSystemMessageToChat(@Payload SystemMessagePayload systemMessagePayload) {
          log.debug(String.format("Received system text %s", systemMessagePayload.toString()));
          chatMessagingService.sendSystemMessageToChat(systemMessagePayload);
     }
}
