package com.streamacho.meeting.transmission.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class SessionDTO {

     private String token;

     private String sessionId;
}
