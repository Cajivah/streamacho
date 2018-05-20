package com.streamacho.api.user.model.event;

import com.streamacho.api.user.model.entity.UserCredentials;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@ToString(callSuper = true)
public class OnPasswordResetEvent extends ApplicationEvent {

     public OnPasswordResetEvent(Object source, Locale locale, UserCredentials userCredentials) {
          super(source);
          this.locale = locale;
          this.userCredentials = userCredentials;
     }

     private Locale locale;

     private UserCredentials userCredentials;
}
