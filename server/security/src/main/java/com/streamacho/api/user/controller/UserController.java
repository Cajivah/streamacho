package com.streamacho.api.user.controller;

import com.streamacho.api.user.model.dto.LockUserDTO;
import com.streamacho.api.user.model.dto.UserDetailsDTO;
import com.streamacho.api.user.model.dto.UserRegistrationDTO;
import com.streamacho.api.user.service.UserCredentialsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

     private final UserCredentialsService userCredentialsService;

     @PostMapping
     @ResponseStatus(HttpStatus.CREATED)
     public UserDetailsDTO createUser(@RequestBody @Validated UserRegistrationDTO registrationDTO) {
          return userCredentialsService.createUser(registrationDTO);
     }

     @PatchMapping("/{userId}/lock")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     public void updateUserLock(@PathVariable Long userId,
                                @RequestBody @Validated LockUserDTO lockUserDTO) {
          userCredentialsService.updateUserLock(userId, lockUserDTO);
     }
}
