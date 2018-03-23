package com.streamacho.api.user.model.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Data
@Builder
public class LoginCompleteDTO implements Serializable {

     private Authentication authentication;
     private HttpServletRequest request;
}
