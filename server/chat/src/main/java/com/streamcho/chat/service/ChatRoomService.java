package com.streamcho.chat.service;

import com.streamcho.chat.config.properties.ChatEndpointsProperties;
import com.streamcho.chat.dto.OnlineUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpSubscriptionMatcher;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatRoomService {

     private final ChatEndpointsProperties chatEndpointsProperties;

     private final SimpUserRegistry simpUserRegistry;

     @Autowired
     public ChatRoomService(ChatEndpointsProperties chatEndpointsProperties, SimpUserRegistry simpUserRegistry) {
          this.chatEndpointsProperties = chatEndpointsProperties;
          this.simpUserRegistry = simpUserRegistry;
     }

     public List<OnlineUserDto> getOnlineUsers(Long chatId) {
          SimpSubscriptionMatcher destinationChatIdMatcher = subscription ->
               subscription
                    .getDestination()
                    .equals(chatEndpointsProperties.getChatSubscribePrefix() + "/" + chatId);
          return simpUserRegistry
               .findSubscriptions(destinationChatIdMatcher)
               .stream()
               .map(simpSubscription ->
                    new OnlineUserDto(simpSubscription.getSession().getUser().getName()))
               .distinct()
               .collect(Collectors.toList());

     }
}
