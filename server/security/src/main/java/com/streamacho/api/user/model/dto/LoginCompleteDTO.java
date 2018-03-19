package com.streamacho.api.user.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

@Data
@Builder
public class LoginCompleteDTO {

     private Authentication authentication;
     private HttpServletRequest request;
}
