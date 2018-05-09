package com.streamacho.chat.mappers;

import com.streamacho.chat.dto.UserChatMessagePayload;
import com.streamacho.core.chat.SystemChatMessagePayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatSystemMessageMapper {

     UserChatMessagePayload toChatMessage(SystemChatMessagePayload systemChatMessagePayload);

     @Mapping(target = "sendAt", ignore = true)
     SystemChatMessagePayload toSystemMessage(UserChatMessagePayload systemMessagePayload);

}
