package com.librarysystem.controller;

import com.librarysystem.model.User;
import com.librarysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        try {
            // Role is set from the form input
            if (user.getRole() == null || user.getRole().isEmpty()) {
                return "redirect:/register?error=role";
            }
            // Ensure role has proper prefix
            if (!user.getRole().startsWith("ROLE_")) {
                user.setRole("ROLE_" + user.getRole());
            }
            userService.createUser(user);
            return "redirect:/login?registered";
        } catch (RuntimeException e) {
            return "redirect:/register?error";
        }
    }

    @GetMapping("/home")
    public String dashboard() {
        return "redirect:/dashboard";
    }
}