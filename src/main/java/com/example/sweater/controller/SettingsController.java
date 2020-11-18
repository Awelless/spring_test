package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.exception.UserNotUniqueException;
import com.example.sweater.service.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
            @AuthenticationPrincipal User currentUser,
            Model model
    ) {
        User user = userService.findByUsername(currentUser.getUsername());

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
        User user = userService.findByUsername(currentUser.getUsername());

        model.addAttribute("user", user);

        Map<String, String> errors = new HashMap<>();

        if (!emailValidator.isValid(email)) {
            errors.put("emailError", "Email is invalid");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            errors.put("passwordError", "Wrong password");
        }

        if (!StringUtils.isEmpty(newPassword) && newPassword.length() < 5) {
            errors.put("newPasswordError", "Password should contain at least 5 characters");
        }

        if (!newPassword.equals(passwordConfirm)) {
            errors.put("passwordConfirmError", "Passwords are different");
        }

        if (errors.size() > 0) {
            model.addAllAttributes(errors);
            return "settings";
        }

        boolean isUnique = true;
        boolean isUpdated = false;
        try {
            isUpdated = userService.updateUser(user, newPassword, email);
        } catch (UserNotUniqueException e) {
            isUnique = false;
            model.mergeAttributes(e.getErrors());
        }

        if (!isUnique) {
            return "settings";
        }

        if (!isUpdated) {
            return "redirect:/settings";
        }

        updatePrincipal(user);

        if (!StringUtils.isEmpty(user.getNewEmail()) ||
                (!StringUtils.isEmpty(email) && email.equals(user.getNewEmail()))) {
            model.addAttribute("emailAlert", "Activation code is sent to your new e-mail");
            model.addAttribute("emailAlertType", "primary");
        }

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            model.addAttribute("passwordAlert", "Password is updated");
            model.addAttribute("passwordAlertType", "success");
        }

        return "settings";
    }

    @GetMapping("update/{code}")
    public String updateEmail(
            @AuthenticationPrincipal User currentUser,
            @PathVariable String code,
            Model model)
    {
        User user = userService.updateEmail(code);

        if (user != null && currentUser.equals(user)) {
            updatePrincipal(user);
            model.addAttribute("emailAlert", "Email is updated");
            model.addAttribute("emailAlertType", "success");
        } else {
            model.addAttribute("emailAlert", "Activation code is not found");
            model.addAttribute("emailAlertType", "danger");
        }

        model.addAttribute("user", user);

        return "settings";
    }

    private void updatePrincipal(User user) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
