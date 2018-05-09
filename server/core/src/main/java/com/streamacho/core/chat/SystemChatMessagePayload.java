package com.streamacho.core.chat;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SystemChatMessagePayload {
     private String authorUsername;
     private Long chatId;
     private LocalDateTime sendAt;
     private String text;
}