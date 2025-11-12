package com.github.evp2.resourceserver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@ResponseBody
public class GreetingController {
  private final GreetingService greetingService;

  public GreetingController(GreetingService greetingService) {
    this.greetingService = greetingService;
  }

  @GetMapping("/")
  public Map<String, String> index() {
    return greetingService.greet();
  }
}
