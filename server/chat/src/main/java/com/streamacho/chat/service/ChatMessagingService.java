package com.streamacho.chat.service;

import com.streamacho.chat.dto.UserChatMessagePayload;
import com.streamacho.chat.mappers.ChatSystemMessageMapper;
import com.streamacho.core.chat.SystemChatMessagePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ChatMessagingService {

     private final SimpMessageSendingOperations messageOps;
     private final ChatSystemMessageMapper mapper;
     private final ChatEndpointDestinationResolver chatEndpointDestinationResolver;

     public void sendSystemMessageToChat(SystemChatMessagePayload payload) {
          UserChatMessagePayload chatMessagePayload = mapper.toChatMessage(payload);
          log.debug(String.format("Sending message to chat %s", chatMessagePayload.toString()));
          String destination = chatEndpointDestinationResolver.getDestination(payload.getChatId());
          messageOps.convertAndSend(destination, chatMessagePayload);
     }
}
