package com.streamacho.meeting.invitation.model.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@Validated
public class InvitationsDTO {

    @NotNull
    private List<InvitationDTO> invitations;
}
