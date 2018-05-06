package com.streamacho.chat.mappers;

import com.streamacho.chat.dto.SystemMessagePayload;
import com.streamacho.chat.dto.UserChatMessagePayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatSystemMessageMapper {

     UserChatMessagePayload toChatMessage(SystemMessagePayload systemMessagePayload);

     SystemMessagePayload toSystemMessage(UserChatMessagePayload systemMessagePayload);

}
