package com.github.evp2.resourceserver.controller;

import com.github.evp2.resourceserver.alert.AlertModel;
import com.github.evp2.resourceserver.alert.AlertService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {

  private final AlertService alertService;

  public ApplicationController(AlertService alertService) {
    this.alertService = alertService;
  }

  @GetMapping("/")
  public String index(@AuthenticationPrincipal Jwt jwt,
                      Model model) {
    model.addAttribute("userName", jwt.getSubject());
    model.addAttribute("clientName", jwt.getClaim("aud"));
    model.addAttribute("totalAlerts", alertService.getAllAlerts());
    model.addAttribute("falsePositiveAlerts", alertService.getAllAlerts().stream().filter(AlertModel::isFalsePositive).toList());
    model.addAttribute("resolvedAlerts", alertService.getAllAlerts().stream().filter(AlertModel::isResolved).toList());
    model.addAttribute("userAttributes", jwt.getClaims());
    return "index";
  }
}
