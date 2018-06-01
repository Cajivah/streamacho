package com.streamacho.chatdb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class ChatMessageDto {
     private ZonedDateTime sendAt;
     private String authorUsername;
     private String text;
}
