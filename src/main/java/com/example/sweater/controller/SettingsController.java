package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/settings")
public class SettingsController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String getProfile(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        model.addAttribute("user", user);

        return "settings";
    }

    @PostMapping
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String password2,
            Model model
    ) {
        model.addAttribute("user", user);

        if (!password.equals(password2)) {
            model.addAttribute("password2Error", "Passwords are different");
            return "settings";
        }

        boolean isUpdated = userService.updateProfile(user, password, email);

        if (!isUpdated) {
            return "redirect:/settings";
        }

        model.addAttribute("message", "Account is updated");

        return "settings";
    }
}
