package com.streamacho.meeting.mail.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomInvitationMessageKeys {

     public static final String MAIN_MESSAGE_TEMPLATE = "mail.invitation.mainMessage";
     public static final String STARTING_GREETER_TEMPLATE = "mail.invitation.startingGreeterTemplate";
     public static final String TITLE = "mail.invitation.title";

     public static final String ACTION_URL_VARIABLE = "actionURL";
     public static final String MAIN_MESSAGE_VARIABLE = "mainMessage";
     public static final String STARTING_GREETER_VARIABLE = "startingGreeter";

     public static final String TEMPLATE_NAME = "RoomInvitationEmail";
}
