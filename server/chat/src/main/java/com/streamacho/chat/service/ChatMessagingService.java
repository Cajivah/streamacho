package com.streamacho.chat.service;

import com.streamacho.chat.dto.SystemMessagePayload;
import com.streamacho.chat.dto.UserChatMessagePayload;
import com.streamacho.chat.mappers.ChatSystemMessageMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ChatMessagingService {

     private final SimpMessageSendingOperations messageOps;
     private final ChatSystemMessageMapper mapper;

     public void sendSystemMessageToChat(SystemMessagePayload payload) {
          UserChatMessagePayload chatMessagePayload = mapper.toChatMessage(payload);
          log.info(String.format("Sending message to chat %s", chatMessagePayload.toString()));
          messageOps.convertAndSend(inferDestination(payload), chatMessagePayload);
     }

     private String inferDestination(SystemMessagePayload payload) {
          return String.format("/chat/%s", payload.getChatId());
     }
}
