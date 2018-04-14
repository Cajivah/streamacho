package com.streamcho.api.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class CorsConfig {

     private static final String ALLOWED_HEADERS = "X-XSRF-TOKEN, x-wa-p, Content-Type";
     private static final String ALLOWED_METHODS = "*";
     private static final String MAX_AGE = "3600";

     @Bean
     public WebFilter corsFilter() {
          return (ServerWebExchange ctx, WebFilterChain chain) -> {
               ServerHttpRequest request = ctx.getRequest();
               if (CorsUtils.isCorsRequest(request)) {
                    ServerHttpResponse response = ctx.getResponse();
                    HttpHeaders headers = response.getHeaders();
                    headers.add("Access-Control-Allow-Origin", ctx.getRequest().getHeaders().getOrigin());
                    headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
                    headers.add("Access-Control-Allow-Credentials", "true");
                    headers.add("Access-Control-Max-Age", MAX_AGE);
                    headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
                    if (request.getMethod() == HttpMethod.OPTIONS) {
                         response.setStatusCode(HttpStatus.OK);
                         return Mono.empty();
                    }
               }
               return chain.filter(ctx);
          };
     }
}