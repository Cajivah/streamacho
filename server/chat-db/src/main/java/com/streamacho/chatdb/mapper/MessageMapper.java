package com.streamacho.chatdb.mapper;

import com.streamacho.chatdb.dto.ChatMessageDto;
import com.streamacho.chatdb.dto.SystemChatMessagePayload;
import com.streamacho.chatdb.model.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

     @Mapping(target = "id", ignore = true)
     @Mapping(target = "sendAt", ignore = true)
     ChatMessage map(SystemChatMessagePayload systemChatMessagePayload);

     ChatMessageDto map(ChatMessage chatMessage);
}
