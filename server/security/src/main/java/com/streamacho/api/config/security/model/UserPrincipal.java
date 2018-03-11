package com.streamacho.api.config.security.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserPrincipal {

     private String username;
}
