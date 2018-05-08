package com.streamacho.chatdb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ChatMessageDto {
     private LocalDateTime sendAt;
     private String authorUsername;
     private String text;
}
