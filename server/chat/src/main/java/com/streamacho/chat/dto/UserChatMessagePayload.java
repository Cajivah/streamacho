package com.streamacho.chat.dto;

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
     private Long chatId;
     @Nullable
     private String authorUsername;
     private String text;
}
