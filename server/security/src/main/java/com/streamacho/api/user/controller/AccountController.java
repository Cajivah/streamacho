package com.streamacho.api.user.controller;

import com.streamacho.api.user.model.dto.ChangePasswordDTO;
import com.streamacho.api.user.model.dto.EmailDTO;
import com.streamacho.api.user.model.dto.ResetPasswordDTO;
import com.streamacho.api.user.model.dto.UserDetailsDTO;
import com.streamacho.api.user.model.dto.UserRegistrationDTO;
import com.streamacho.api.user.model.entity.UserCredentials;
import com.streamacho.api.user.model.event.OnPasswordResetEvent;
import com.streamacho.api.user.model.event.OnRegistrationCompleteEvent;
import com.streamacho.api.user.service.UserCredentialsService;
import com.streamacho.api.user.service.VerificationTokenService;
import io.swagger.annotations.ApiImplicitParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

     private final ApplicationEventPublisher eventPublisher;
     private final UserCredentialsService userCredentialsService;
     private final VerificationTokenService verificationTokenService;

     @PostMapping
     @ResponseStatus(HttpStatus.CREATED)
     public UserDetailsDTO registerNewUser(
          @RequestBody @Validated UserRegistrationDTO userRegistrationDTO,
          HttpServletRequest request) {
          final UserDetailsDTO user = userCredentialsService.createUser(userRegistrationDTO);
          final Locale locale = request.getLocale();
          eventPublisher.publishEvent(new OnRegistrationCompleteEvent(this, user, locale));
          return user;
     }

     @PatchMapping("/password")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     public void changePassword(@RequestBody @Validated ChangePasswordDTO changePasswordDTO,
                                @AuthenticationPrincipal UserDetails user) {
          userCredentialsService.changePassword(changePasswordDTO, user);
     }

     @PostMapping("/reset-password")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     public void createPasswordResetToken(@RequestBody @Validated EmailDTO emailDTO,
                                          HttpServletRequest request) {
          UserCredentials user = userCredentialsService.findByEmail(emailDTO.getEmail());
          Locale locale = request.getLocale();
          eventPublisher.publishEvent(new OnPasswordResetEvent(this, locale, user));
     }

     @PatchMapping("/reset-password")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     public void changePassword(@RequestBody @Validated ResetPasswordDTO resetPasswordDTO) {
          verificationTokenService.resetPassword(resetPasswordDTO);
     }

     @GetMapping("/me")
     public UserDetailsDTO getAuthenticatedUserDetails(@AuthenticationPrincipal UserDetails user) {
          return userCredentialsService.getAuthenticatedUserDetails(user);
     }

     @GetMapping
     @ApiImplicitParam(name = "query", value = "email or username")
     public Page<UserDetailsDTO> getUsers(@RequestParam String query,
                                          Pageable pageable) {
          return userCredentialsService.getUsersDTO(query, pageable);
     }
}
