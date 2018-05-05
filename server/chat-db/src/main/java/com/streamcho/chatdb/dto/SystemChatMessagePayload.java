package com.streamcho.chatdb.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SystemChatMessagePayload {
     private String username;
     private Long chatId;
     private LocalDateTime sendAt;
     private String text;
}
