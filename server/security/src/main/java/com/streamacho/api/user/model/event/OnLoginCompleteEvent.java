package com.streamacho.api.user.model.event;

import com.streamacho.api.user.model.dto.LoginCompleteDTO;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@ToString(callSuper = true)
public class OnLoginCompleteEvent extends ApplicationEvent {

     private LoginCompleteDTO loginCompleteDTO;

     public OnLoginCompleteEvent(Object source, LoginCompleteDTO loginCompleteDTO) {
          super(source);
          this.loginCompleteDTO = loginCompleteDTO;
     }
}
