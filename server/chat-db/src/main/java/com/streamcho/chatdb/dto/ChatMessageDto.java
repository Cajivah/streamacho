package com.streamcho.chatdb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ChatMessageDto {
     private LocalDateTime sendAt;
     private String username;
     private String text;
}
