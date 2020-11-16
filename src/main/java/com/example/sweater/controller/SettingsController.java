package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/settings")
public class SettingsController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailValidator emailValidator;

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
            @AuthenticationPrincipal User currentUser,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String newPassword,
            @RequestParam String passwordConfirm,
            Model model
    ) {
        model.addAttribute("user", currentUser);

        Map<String, String> errors = new HashMap<>();

        if (!emailValidator.isValid(email)) {
            errors.put("emailError", "Email is invalid");
        }

        if (!passwordEncoder.matches(password, currentUser.getPassword())) {
            errors.put("passwordError", "Wrong password");
        }

        if (!newPassword.equals(passwordConfirm)) {
            errors.put("passwordConfirmError", "Passwords are different");
        }

        if (errors.size() > 0) {
            model.addAllAttributes(errors);
            return "settings";
        }

        String oldEmail = currentUser.getEmail();
        String oldPassword = currentUser.getPassword();

        boolean isUpdated = userService.updateUser(currentUser, password, email);

        if (!isUpdated) {
            return "redirect:/settings";
        }

        return "settings";
    }
}
