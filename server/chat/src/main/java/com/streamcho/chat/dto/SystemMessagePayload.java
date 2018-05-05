package com.streamcho.chat.dto;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SystemMessagePayload {
     private String username;
     private Long chatId;
     private String text;
}
