package com.streamcho.chat;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.Optional;

@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class AuthenticationChannelInterceptor extends ChannelInterceptorAdapter {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor stompHeaderAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        StompCommand command = stompHeaderAccessor.getCommand();
        System.out.println("Odebrano " + message);

        if (command.equals(StompCommand.SUBSCRIBE)) {
            String destination = stompHeaderAccessor.getDestination();
            String chatId = extractChatId(destination)
                    .orElseThrow(RuntimeException::new);
            System.out.println("text sent to " + chatId);
        }
        return super.preSend(message, channel);
    }

    private Optional<String> extractChatId(String destination) {
        String[] separatedValues = destination.split("/");
        return Optional.ofNullable(separatedValues[separatedValues.length - 1]);
    }
}
