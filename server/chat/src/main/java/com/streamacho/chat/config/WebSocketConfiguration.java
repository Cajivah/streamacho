package com.streamacho.chat.config;

import com.streamacho.chat.config.properties.ChatEndpointsProperties;
import com.streamacho.chat.config.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

     private final ChatEndpointsProperties chatEndpointsProperties;
     private final SecurityProperties securityProperties;

     @Override
     protected boolean sameOriginDisabled() {
          return true;
     }

     @Override
     public void registerStompEndpoints(StompEndpointRegistry registry) {
          registry.addEndpoint(chatEndpointsProperties.getWebSocketEndpoint())
               .setAllowedOrigins(securityProperties.getFrontendOrigin())
               .withSockJS();
     }

     @Override
     protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
          messages
               .nullDestMatcher().permitAll()
               .simpSubscribeDestMatchers(chatEndpointsProperties.getChatSubscribePrefix() + "/**").permitAll()
               .simpMessageDestMatchers(chatEndpointsProperties.getChatSendPrefix() + "/**").authenticated()
               .anyMessage().denyAll();
     }

     @Override
     public void configureMessageBroker(MessageBrokerRegistry registry) {
          registry.enableSimpleBroker(chatEndpointsProperties.getChatSubscribePrefix());
          registry.setApplicationDestinationPrefixes("/");
     }
}
