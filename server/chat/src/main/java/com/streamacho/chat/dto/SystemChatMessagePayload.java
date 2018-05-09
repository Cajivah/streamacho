package com.streamacho.chat.dto;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SystemChatMessagePayload {
     private Long chatId;
     private String authorUsername;
     private String text;
}
