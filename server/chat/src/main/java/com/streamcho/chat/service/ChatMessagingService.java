package com.streamcho.chat.service;

import com.streamcho.chat.dto.SystemMessagePayload;
import com.streamcho.chat.dto.UserChatMessagePayload;
import com.streamcho.chat.mappers.ChatSystemMessageMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ChatMessagingService {

    private final SimpMessageSendingOperations messageOps;

    private final ChatSystemMessageMapper mapper;

    @Autowired
    public ChatMessagingService(SimpMessageSendingOperations messageOps, ChatSystemMessageMapper mapper) {
        this.messageOps = messageOps;
        this.mapper = mapper;
    }

    public void sendSystemMessageToChat(SystemMessagePayload payload) {
        UserChatMessagePayload chatMessagePayload = mapper.toChatMessage(payload);
        log.info(String.format("Sending message to chat %s", chatMessagePayload.toString()));
        messageOps.convertAndSend(createDestination(payload), chatMessagePayload);
    }

    private String createDestination(SystemMessagePayload payload) {
        return String.format("/chat/%s", payload.getChatId());
    }
}
