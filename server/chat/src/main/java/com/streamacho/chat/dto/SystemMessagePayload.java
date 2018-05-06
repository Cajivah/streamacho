package com.streamacho.chat.dto;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SystemMessagePayload {
     private Long chatId;
     private String username;
     private String text;
}
