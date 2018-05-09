package com.streamacho.chatdb.service;

import com.streamacho.chatdb.dto.ChatMessageDto;
import com.streamacho.chatdb.dto.SystemChatMessagePayload;
import com.streamacho.chatdb.mapper.MessageMapper;
import com.streamacho.chatdb.model.ChatMessage;
import com.streamacho.chatdb.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessagesService {

     private final ChatMessageRepository repository;
     private final MessageMapper messageMapper;

     public void saveMessage(SystemChatMessagePayload systemChatMessagePayload) {
          ChatMessage chatMessage = messageMapper.map(systemChatMessagePayload);
          repository.save(chatMessage);
     }

     public Page<ChatMessageDto> getChatMessages(Long chatId, Pageable pageable) {
          Page<ChatMessage> messages = repository.findAllByChatId(chatId, pageable);
          return messages.map(messageMapper::map);
     }
}
