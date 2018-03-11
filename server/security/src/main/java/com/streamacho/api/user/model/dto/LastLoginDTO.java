package com.streamacho.api.user.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
@Builder
public class LastLoginDTO {

     private String username;
     private HttpServletRequest request;
}
