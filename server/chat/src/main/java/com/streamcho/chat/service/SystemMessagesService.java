package com.streamcho.chat.service;

import com.streamcho.chat.dto.SystemMessagePayload;
import com.streamcho.chat.dto.UserChatMessagePayload;
import com.streamcho.chat.mappers.ChatSystemMessageMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SystemMessagesService {

     private final Processor processor;

     private final ChatSystemMessageMapper mapper;

     @Autowired
     public SystemMessagesService(Processor processor, ChatSystemMessageMapper mapper) {
          this.processor = processor;
          this.mapper = mapper;
     }

     public void sendChatMessageToSystem(UserChatMessagePayload userChatMessagePayload) {
          SystemMessagePayload payload = mapper.toSystemMessage(userChatMessagePayload);
          Message<SystemMessagePayload> systemMessage = MessageBuilder.withPayload(payload).build();
          log.info("Sending text to system with payload " + payload.toString());
          sendMessage(systemMessage);
     }

     private void sendMessage(Message<SystemMessagePayload> systemMessage) {
          processor.output().send(systemMessage);
     }
}
