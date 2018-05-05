package com.streamcho.chat.mappers;

import com.streamcho.chat.dto.SystemMessagePayload;
import com.streamcho.chat.dto.UserChatMessagePayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatSystemMessageMapper {

     UserChatMessagePayload toChatMessage(SystemMessagePayload systemMessagePayload);

     SystemMessagePayload toSystemMessage(UserChatMessagePayload systemMessagePayload);

}
