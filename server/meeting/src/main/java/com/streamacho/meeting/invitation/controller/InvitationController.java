package com.streamacho.meeting.invitation.controller;

import com.streamacho.meeting.invitation.model.dto.InvitationDTO;
import com.streamacho.meeting.invitation.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InvitationController {

     private final InvitationService invitationService;

     @PostMapping("/rooms/{roomId}/invitations")
     @ResponseStatus(HttpStatus.NO_CONTENT)
     public void invite(@PathVariable Long roomId,
                        @AuthenticationPrincipal UserDetails issuer,
                        @RequestBody @Valid InvitationDTO invitationDTO,
                        HttpServletRequest request) {
          Locale locale = request.getLocale();
          invitationService.inviteUser(roomId, issuer, invitationDTO, locale);
     }
}
