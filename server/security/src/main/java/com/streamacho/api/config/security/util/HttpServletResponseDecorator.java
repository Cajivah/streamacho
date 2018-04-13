package com.streamacho.api.config.security.util;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.streamacho.api.util.HttpHeaderValue.APPLICATION_JSON;

public class HttpServletResponseDecorator extends javax.servlet.http.HttpServletResponseWrapper {
     /**
      * Constructs a response adaptor wrapping the given response.
      *
      * @param response The response to be wrapped
      * @throws IllegalArgumentException if the response is null
      */
     public HttpServletResponseDecorator(HttpServletResponse response) {
          super(response);
     }

     public void setJSONBody(String body) throws IOException {
          setContentType(APPLICATION_JSON);
          getResponse().getWriter().write(body);
     }

     public void setStatusOK() {
          setStatus(HttpServletResponse.SC_OK);
     }
}
