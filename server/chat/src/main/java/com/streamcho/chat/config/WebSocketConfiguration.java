package com.streamcho.chat.config;

import com.streamcho.chat.config.properties.ChatEndpointsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

     private final ChatEndpointsProperties chatEndpointsProperties;

     @Autowired
     public WebSocketConfiguration(ChatEndpointsProperties chatEndpointsProperties) {
          this.chatEndpointsProperties = chatEndpointsProperties;
     }

     @Override
     protected boolean sameOriginDisabled() {
          return true;
     }

     @Override
     public void registerStompEndpoints(StompEndpointRegistry registry) {
          registry.addEndpoint(chatEndpointsProperties.getWebSocketEndpoint()).setAllowedOrigins("*").withSockJS();
     }

     @Override
     protected void customizeClientInboundChannel(ChannelRegistration registration) {
          //registration.interceptors(new AuthenticationChannelInterceptor());
          super.customizeClientInboundChannel(registration);
     }

     @Override
     protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
          messages
               .nullDestMatcher().permitAll()
               .simpSubscribeDestMatchers(chatEndpointsProperties.getChatSubscribePrefix() + "/**").permitAll()
               .simpMessageDestMatchers(chatEndpointsProperties.getChatSendPrefix() + "/**").permitAll()
               .anyMessage().denyAll();
     }

     @Override
     public void configureMessageBroker(MessageBrokerRegistry registry) {
          registry.enableSimpleBroker(chatEndpointsProperties.getChatSubscribePrefix());
          registry.setApplicationDestinationPrefixes("/");
     }
}
