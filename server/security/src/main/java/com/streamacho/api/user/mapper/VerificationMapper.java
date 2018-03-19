package com.streamacho.api.user.mapper;

import com.streamacho.api.user.model.dto.VerificationMailDTO;
import com.streamacho.api.user.model.entity.VerificationToken;
import com.streamacho.api.user.model.event.OnRegistrationCompleteEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class VerificationMapper {

     @Mapping(target = "username", source = "token.user.username")
     @Mapping(target = "address", source = "token.user.email")
     @Mapping(target = "token", source = "token.token")
     @Mapping(target = "locale", source = "event.locale")
     public abstract VerificationMailDTO verificationMailDTO(VerificationToken token,
                                                             OnRegistrationCompleteEvent event);
}
