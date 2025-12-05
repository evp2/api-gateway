package com.github.evp2.gatewaymvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DefaultController {
  @GetMapping("/")
  public String index() {
    return "redirect:/api";
  }

  @GetMapping("/fallback")
  @ResponseBody
  public String fallback() {
    return "There was a downstream error, this is a fallback.";
  }
}
