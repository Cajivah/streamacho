package com.streamacho.api.user.mapper;

import com.streamacho.api.user.model.dto.PasswordResetMailDTO;
import com.streamacho.api.user.model.entity.VerificationToken;
import com.streamacho.api.user.model.event.OnPasswordResetEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PasswordResetMapper {

     @Mapping(target = "username", source = "token.user.username")
     @Mapping(target = "address", source = "token.user.email")
     @Mapping(target = "token", source = "token.token")
     @Mapping(target = "locale", source = "event.locale")
     PasswordResetMailDTO toPasswordResetMailDTO(VerificationToken token,
                                                OnPasswordResetEvent event);
}
