package com.github.evp2.resourceserver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;

@Controller
@ResponseBody
public class UserController {

  @GetMapping("/user")
  public Map<String, String> getUser(Principal principal) {
    return Map.of("username", principal.getName());
  }
}
