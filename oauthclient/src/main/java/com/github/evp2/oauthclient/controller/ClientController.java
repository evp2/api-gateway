package com.github.evp2.oauthclient.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ResponseBody
public class ClientController {
  @GetMapping("/")
  public Map<String, String> index(Authentication authentication) {
    return Map.of("Welcome", authentication.getName());
  }

  @RequestMapping("/fallback")
  public String fallback() {
    return "There was a downstream error, this is a fallback.";
  }

  @GetMapping("/logout")
  public String logout(Authentication authentication) {
    if (authentication != null) {
      authentication.setAuthenticated(false);
    }
    return "Logged out successfully.";
  }
}
