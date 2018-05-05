package com.streamcho.chatdb.mapper;

import com.streamcho.chatdb.dto.ChatMessageDto;
import com.streamcho.chatdb.dto.SystemChatMessagePayload;
import com.streamcho.chatdb.model.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

     @Mapping(target = "id", ignore = true)
     @Mapping(target = "sendAt", ignore = true)
     ChatMessage map(SystemChatMessagePayload systemChatMessagePayload);

     ChatMessageDto map(ChatMessage chatMessage);
}
