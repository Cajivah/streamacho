package com.streamcho.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChatMessagePayload {
     private String chatId;
     @Nullable
     private String username;
     private String text;
}
