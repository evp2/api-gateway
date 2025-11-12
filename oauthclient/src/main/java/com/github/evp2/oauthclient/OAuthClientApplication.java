package com.github.evp2.oauthclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
public class OAuthClientApplication {
  public static void main(String[] args) {
    SpringApplication.run(OAuthClientApplication.class, args);
  }

  @Bean
  SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http
        .authorizeExchange(exchanges -> exchanges.anyExchange().authenticated())
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .oauth2Login(Customizer.withDefaults())
        .oauth2Client(Customizer.withDefaults());
    return http.build();
  }
}
