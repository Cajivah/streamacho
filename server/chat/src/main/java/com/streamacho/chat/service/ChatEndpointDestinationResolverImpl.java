package com.streamacho.chat.service;

import com.streamacho.chat.config.properties.ChatEndpointsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatEndpointDestinationResolverImpl implements ChatEndpointDestinationResolver {

     private final ChatEndpointsProperties chatEndpointsProperties;

     @Override
     public String getDestination(Long chatId) {
          return chatEndpointsProperties.getChatSubscribePrefix() + "/" + chatId;
     }
}
