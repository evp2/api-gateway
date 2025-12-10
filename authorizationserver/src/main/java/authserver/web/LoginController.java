package authserver.web;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

  private final UserDetailsManager userDetailsManager;
  private final PasswordEncoder passwordEncoder;

  public LoginController(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
    this.userDetailsManager = userDetailsManager;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/login")
  public String loginPage(@RequestParam(value = "registered", required = false) String registered,
                          @RequestParam(value = "error", required = false) String error,
                          @RequestParam(value = "registerError", required = false) String registerError,
                          Model model) {
    model.addAttribute("registered", registered != null);
    model.addAttribute("loginError", error != null);
    model.addAttribute("registerError", registerError);
    return "login";
  }

  @PostMapping("/register")
  public String register(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam("confirmPassword") String confirmPassword,
                         Model model) {
    // Basic validation
    if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
      return "redirect:/login?registerError=Username%20and%20password%20are%20required";
    }
    if (!password.equals(confirmPassword)) {
      return "redirect:/login?registerError=Passwords%20do%20not%20match";
    }

    // Check existing
    if (userDetailsManager.userExists(username)) {
      return "redirect:/login?registerError=User%20already%20exists";
    }

    // Create new user with a default role USER
    String encoded = passwordEncoder.encode(password);
    UserDetails user = User
        .withUsername(username)
        .password(encoded)
        .roles("USER", "READ")
        .build();
    userDetailsManager.createUser(user);

    return "redirect:/login?registered";
  }
}
