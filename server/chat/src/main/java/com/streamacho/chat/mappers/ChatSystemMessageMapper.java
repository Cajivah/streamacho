package com.streamacho.chat.mappers;

import com.streamacho.chat.dto.SystemChatMessagePayload;
import com.streamacho.chat.dto.UserChatMessagePayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatSystemMessageMapper {

     UserChatMessagePayload toChatMessage(SystemChatMessagePayload systemChatMessagePayload);

     SystemChatMessagePayload toSystemMessage(UserChatMessagePayload systemMessagePayload);

}
