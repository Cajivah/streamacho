package com.streamacho.meeting.invitation.model.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.mail.internet.InternetAddress;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Validated
public class InvitationDTO {

     @NotBlank
     private String username;

     @NotNull
     private InternetAddress email;
}
