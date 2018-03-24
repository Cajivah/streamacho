package com.streamacho.api.mail.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VerifyRegistrationMailMessageKeys {

     public static final String TITLE = "mail.verify.title";
     public static final String STARTING_GREETER_TEMPLATE = "mail.verify.startingGreeterTemplate";

     public static final String ACTION_URL_VARIABLE = "actionURL";
     public static final String STARTING_GREETER_VARIABLE = "startingGreeter";

     public static final String TEMPLATE_NAME = "VerificationEmail";
}
