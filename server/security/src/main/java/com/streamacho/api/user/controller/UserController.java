package com.streamacho.api.user.controller;


import com.streamacho.api.user.model.dto.ChangePasswordDTO;
import com.streamacho.api.user.model.dto.UserDetailsDTO;
import com.streamacho.api.user.model.dto.UserRegistrationDTO;
import com.streamacho.api.user.model.event.OnRegistrationCompleteEvent;
import com.streamacho.api.user.service.UserCredentialsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

     private final UserCredentialsService userCredentialsService;
     private final ApplicationEventPublisher eventPublisher;

     @PostMapping()
     @ResponseStatus(HttpStatus.CREATED)
     public void registerNewUser(
          @RequestBody @Validated UserRegistrationDTO userRegistrationDTO,
          HttpServletRequest request) {
          final UserDetailsDTO user = userCredentialsService.createUser(userRegistrationDTO);
          final Locale locale = request.getLocale();
          eventPublisher.publishEvent(new OnRegistrationCompleteEvent(this, user, locale));
     }

     @PatchMapping("/password")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     public void changePassword(@RequestBody @Validated ChangePasswordDTO changePasswordDTO,
                                @AuthenticationPrincipal UserDetails user) {
          userCredentialsService.changePassword(changePasswordDTO, user);
     }

     @GetMapping("/me")
     public UserDetailsDTO getAuthenticatedUserDetails(@AuthenticationPrincipal UserDetails user) {
          return userCredentialsService.getAuthenticatedUserDetails(user);
     }
}
