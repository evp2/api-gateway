package com.github.evp2.oauthclient.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@ResponseBody
public class ClientController {
  @GetMapping("/")
  public Mono<String> index(Authentication authentication) {
    return Mono.just("Welcome " + authentication.getName());
  }

  @RequestMapping("/fallback")
  public Mono<String> fallback() {
    return Mono.just("There was a downstream error, this is a fallback.");
  }

  @GetMapping("/logout")
  public Mono<String> logout() {
    return Mono.just("Logged out successfully.");
  }
}
