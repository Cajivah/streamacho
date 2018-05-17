package com.streamacho.meeting.room.model.event;

import com.streamacho.meeting.room.model.entity.Room;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import javax.mail.internet.InternetAddress;
import java.util.Locale;

@Getter
@ToString(callSuper = true)
public class OnInvitedToRoomEvent extends ApplicationEvent {

     private String issuerUsername;
     private String invitedUsername;
     private InternetAddress invitedEmail;
     private Room room;
     private Locale locale;

     public OnInvitedToRoomEvent(Object source,
                                 String issuerUsername,
                                 InternetAddress invitedEmail,
                                 String invitedUsername,
                                 Room room,
                                 Locale locale) {
          super(source);
          this.issuerUsername = issuerUsername;
          this.invitedEmail = invitedEmail;
          this.invitedUsername = invitedUsername;
          this.room = room;
          this.locale = locale;
     }
}
