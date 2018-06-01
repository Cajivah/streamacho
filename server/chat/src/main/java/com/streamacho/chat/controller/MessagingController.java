package com.streamacho.chat.controller;

import com.streamacho.chat.dto.IncomingMessageDTO;
import com.streamacho.chat.dto.UserChatMessagePayload;
import com.streamacho.chat.service.ChatMessagingService;
import com.streamacho.chat.service.SystemMessagingService;
import com.streamacho.core.chat.SystemChatMessagePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Log4j2
@Controller
@RequiredArgsConstructor
public class MessagingController {

     private final SystemMessagingService systemMessagingService;
     private final ChatMessagingService chatMessagingService;

     @MessageMapping("${streamacho.chat.chatSendPrefix}/{chatId}")
     public void receiveChatMessage(@Payload IncomingMessageDTO message,
                                    @DestinationVariable Long chatId,
                                    Principal user) {
          log.debug("Received text for chat {} with text {}", chatId, message);
          UserChatMessagePayload inputMessagePayload = UserChatMessagePayload.builder()
               .chatId(chatId)
               .text(message.getText())
               .authorUsername(user.getName())
               .build();

          systemMessagingService.sendChatMessageToSystem(inputMessagePayload);
     }

     @StreamListener(Sink.INPUT)
     public void sendSystemMessageToChat(@Payload SystemChatMessagePayload systemChatMessagePayload) {
          log.debug("Received system text {}", systemChatMessagePayload.toString());
          chatMessagingService.sendSystemMessageToChat(systemChatMessagePayload);
     }
}
