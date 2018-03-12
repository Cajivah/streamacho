package com.streamacho.api.user.listener;

import com.streamacho.api.mail.service.MailService;
import com.streamacho.api.user.mapper.VerificationMapper;
import com.streamacho.api.user.model.dto.LoginCompleteDTO;
import com.streamacho.api.user.model.dto.UserDetailsDTO;
import com.streamacho.api.user.model.dto.VerificationMailDTO;
import com.streamacho.api.user.model.entity.VerificationToken;
import com.streamacho.api.user.model.event.OnLoginCompleteEvent;
import com.streamacho.api.user.model.event.OnRegistrationCompleteEvent;
import com.streamacho.api.user.service.UserLoginService;
import com.streamacho.api.user.service.VerificationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserEventListener {

     private final VerificationTokenService verificationTokenService;
     private final VerificationMapper verificationMapper;
     private final UserLoginService userLoginService;
     private final MailService mailService;

     @Async
     @EventListener
     public void handleRegistrationComplete(OnRegistrationCompleteEvent event) {
          final UserDetailsDTO user = event.getUserDetailsDTO();
          final VerificationToken newToken = verificationTokenService.createNewToken(user);
          final VerificationMailDTO mailDTO = verificationMapper.verificationMailDTO(newToken, event);
          mailService.sendVerificationMail(mailDTO);
     }

     @Async
     @EventListener
     public void handleLoginComplete(OnLoginCompleteEvent event) {
          final LoginCompleteDTO loginCompleteDTO = event.getLoginCompleteDTO();
          userLoginService.updateLastLoginDate(loginCompleteDTO);
     }
}
