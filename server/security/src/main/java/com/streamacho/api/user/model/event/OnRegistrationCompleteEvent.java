package com.streamacho.api.user.model.event;

import com.streamacho.api.user.model.dto.UserDetailsDTO;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@ToString(callSuper = true)
public class OnRegistrationCompleteEvent extends ApplicationEvent {

     private UserDetailsDTO userDetailsDTO;
     private Locale locale;

     public OnRegistrationCompleteEvent(Object source,
                                        UserDetailsDTO userDetailsDTO,
                                        Locale locale) {
          super(source);
          this.userDetailsDTO = userDetailsDTO;
          this.locale = locale;
     }
}
