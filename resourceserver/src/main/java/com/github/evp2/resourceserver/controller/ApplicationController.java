package com.github.evp2.resourceserver.controller;

import com.github.evp2.resourceserver.alert.AlertModel;
import com.github.evp2.resourceserver.alert.AlertService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
  @GetMapping("/")
  public String index(@AuthenticationPrincipal Jwt jwt, Model model) {
    model.addAttribute("token", jwt.getTokenValue());
    model.addAttribute("userName", jwt.getSubject());
    model.addAttribute("clientName", jwt.getClaim("aud"));
    model.addAttribute("totalAlerts", alertService.getAllAlerts().size());
    model.addAttribute("falsePositiveAlerts", alertService.getAllAlerts().stream().filter(AlertModel::isFalsePositive).toList().size());
    model.addAttribute("resolvedAlerts", alertService.getAllAlerts().stream().filter(AlertModel::isResolved).toList().size());
    model.addAttribute("userAttributes", jwt.getClaims());
    return "index";
  }
}
