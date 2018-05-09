package com.streamacho.chat.service;

import com.streamacho.chat.dto.UserChatMessagePayload;
import com.streamacho.chat.mappers.ChatSystemMessageMapper;
import com.streamacho.core.chat.SystemChatMessagePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class SystemMessagingService {

     private final Processor processor;
     private final ChatSystemMessageMapper mapper;

     public void sendChatMessageToSystem(UserChatMessagePayload userChatMessagePayload) {
          SystemChatMessagePayload payload = mapper.toSystemMessage(userChatMessagePayload);
          Message<SystemChatMessagePayload> systemMessage = MessageBuilder.withPayload(payload).build();
          log.debug("Sending text to system with payload {}", payload.toString());
          sendMessage(systemMessage);
     }

     private void sendMessage(Message<SystemChatMessagePayload> systemMessage) {
          processor.output().send(systemMessage);
     }
}
