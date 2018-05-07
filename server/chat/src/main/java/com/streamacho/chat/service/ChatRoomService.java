package com.streamacho.chat.service;

import com.streamacho.chat.dto.OnlineUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.user.SimpSubscriptionMatcher;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

     private final SimpUserRegistry simpUserRegistry;
     private final ChatEndpointDestinationResolver chatEndpointDestinationResolver;

     public List<OnlineUserDto> getOnlineUsers(Long chatId) {
          SimpSubscriptionMatcher chatIdMatcher = createChatIdMatcher(chatId);
          return simpUserRegistry
               .findSubscriptions(chatIdMatcher)
               .stream()
               .map(simpSubscription ->
                    new OnlineUserDto(simpSubscription.getSession().getUser().getName()))
               .distinct()
               .collect(toList());

     }

     private SimpSubscriptionMatcher createChatIdMatcher(Long chatId) {
          return subscription ->
               subscription
                    .getDestination()
                    .equals(chatEndpointDestinationResolver.getDestination(chatId));
     }
}
