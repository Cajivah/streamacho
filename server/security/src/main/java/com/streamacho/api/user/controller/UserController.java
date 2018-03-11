package com.streamacho.api.user.controller;


import com.streamacho.api.user.model.dto.ChangePasswordDTO;
import com.streamacho.api.user.model.dto.UserDetailsDTO;
import com.streamacho.api.user.model.dto.UserRegistrationDTO;
import com.streamacho.api.user.service.UserCredentialsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

     private final UserCredentialsService userCredentialsService;

     @PostMapping()
     @ResponseStatus(HttpStatus.CREATED)
     public void registerNewUser(
          @RequestBody @Validated UserRegistrationDTO userRegistrationDTO) {
          userCredentialsService.createUser(userRegistrationDTO);
     }

     @PutMapping("/password-change")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     public void changePassword(@RequestBody @Validated ChangePasswordDTO changePasswordDTO,
                                Authentication authentication) {
          userCredentialsService.changePassword(changePasswordDTO, authentication);
     }

     @GetMapping("/me")
     public UserDetailsDTO getAuthenticatedUserDetails(Authentication authentication) {
          return userCredentialsService.getAuthenticatedUserDetails(authentication);
     }
}
