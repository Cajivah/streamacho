package com.streamacho.api.user.controller;


import com.streamacho.api.user.model.dto.ChangePasswordDTO;
import com.streamacho.api.user.model.dto.UserDetailsDTO;
import com.streamacho.api.user.model.dto.UserRegistrationDTO;
import com.streamacho.api.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

     private final UserService userService;

     @PostMapping()
     @ResponseStatus(HttpStatus.CREATED)
     public void registerNewUser(
          @RequestBody @Validated UserRegistrationDTO userRegistrationDTO) {
          userService.createUser(userRegistrationDTO);
     }

     @PutMapping("/password-change")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     public void changePassword(@RequestBody @Validated ChangePasswordDTO changePasswordDTO,
                                Authentication authentication) {
          userService.changePassword(changePasswordDTO, authentication);
     }

     @GetMapping("/me")
     public UserDetailsDTO getAuthenticatedUserDetails(Authentication authentication) {
          return userService.getAuthenticatedUserDetails(authentication);
     }
}
