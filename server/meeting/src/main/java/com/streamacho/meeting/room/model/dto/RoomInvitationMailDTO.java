package com.streamacho.meeting.room.model.dto;

import lombok.Data;

import javax.mail.internet.InternetAddress;
import java.util.Locale;

@Data
public class RoomInvitationMailDTO {

     private Locale locale;

     private String organiserUsername;

     private InternetAddress recipientAddress;

     private String recipientUsername;

     private Long roomId;

     private String roomName;
}
