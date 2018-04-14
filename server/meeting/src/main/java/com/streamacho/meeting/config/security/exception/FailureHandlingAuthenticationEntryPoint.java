package com.streamacho.meeting.config.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamacho.meeting.util.exception.model.dto.ErrorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class FailureHandlingAuthenticationEntryPoint implements AuthenticationEntryPoint {

     private final ObjectMapper objectMapper;

     @Override
     public void commence(HttpServletRequest request,
                          HttpServletResponse response,
                          AuthenticationException authException) throws IOException {

          response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
          final ErrorDTO error = new ErrorDTO(authException.getMessage());
          response.getWriter().print(objectMapper.writeValueAsString(error));
     }
}
