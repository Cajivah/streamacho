package com.streamacho.chat.service;

import com.streamacho.chat.config.properties.ChatEndpointsProperties;
import com.streamacho.chat.dto.OnlineUserDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpSubscriptionMatcher;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ChatRoomService {

     private final ChatEndpointsProperties chatEndpointsProperties;
     private final SimpUserRegistry simpUserRegistry;

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
