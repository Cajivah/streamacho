package com.streamacho.chat.controller;

import com.streamacho.chat.dto.SystemMessagePayload;
import com.streamacho.chat.dto.UserChatMessagePayload;
import com.streamacho.chat.service.ChatMessagingService;
import com.streamacho.chat.service.SystemMessagesService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Log4j2
@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MessagingController {

     private final SystemMessagesService systemMessagesService;
     private final ChatMessagingService chatMessagingService;

     @MessageMapping("${streamacho.chat.chatSendPrefix}/{chatId}")
     public void receiveChatMessage(@Payload String message,
                                    @DestinationVariable Long chatId,
                                    Principal user) {
          log.debug(String.format("Received text for chat %s with text %s", chatId, message));
          UserChatMessagePayload inputMessagePayload = UserChatMessagePayload.builder()
               .chatId(chatId)
               .text(message)
               .username(user.getName())
               .build();

          systemMessagesService.sendChatMessageToSystem(inputMessagePayload);
     }

     @StreamListener(Sink.INPUT)
     public void sendSystemMessageToChat(@Payload SystemMessagePayload systemMessagePayload) {
          log.debug(String.format("Received system text %s", systemMessagePayload.toString()));
          chatMessagingService.sendSystemMessageToChat(systemMessagePayload);
     }
}
