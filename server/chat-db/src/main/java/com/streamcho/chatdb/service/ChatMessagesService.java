package com.streamcho.chatdb.service;

import com.streamcho.chatdb.dto.ChatMessageDto;
import com.streamcho.chatdb.dto.SystemChatMessagePayload;
import com.streamcho.chatdb.mapper.MessageMapper;
import com.streamcho.chatdb.model.ChatMessage;
import com.streamcho.chatdb.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ChatMessagesService {

     private final ChatMessageRepository repository;

     private final MessageMapper messageMapper;

     @Autowired
     public ChatMessagesService(ChatMessageRepository repository, MessageMapper messageMapper) {
          this.repository = repository;
          this.messageMapper = messageMapper;
     }

     public void saveMessage(SystemChatMessagePayload systemChatMessagePayload) {
          ChatMessage chatMessage = messageMapper.map(systemChatMessagePayload);
          repository.save(chatMessage);
     }

     public Page<ChatMessageDto> getChatMessages(String chatId, Pageable pageable) {
          Page<ChatMessage> messages = repository.getByChatId(chatId, pageable);
          return messages.map(messageMapper::map);
     }
}
