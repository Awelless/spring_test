package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailValidator emailValidator;

    @GetMapping("/login")
    public String getLoginPage(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false, defaultValue = "false") Boolean error,
            Model model
    ) {
        if (currentUser != null) {
            return "redirect:/";
        }

        if (error) {
            model.addAttribute("loginError", true);
        }

        return "login";
    }

    @GetMapping("/forgot_password")
    public String getForgotPage() {
        return "forgotPassword";
    }

    @PostMapping("/forgot_password")
    public String forgotPassword(
            @RequestParam String email,
            Model model
    ) {
        if (!emailValidator.isValid(email)) {
            model.addAttribute("emailError", "Email is invalid");
            return "forgotPassword";
        }

        User user = userService.findByEmail(email);

        if (user == null ||
                (!StringUtils.isEmpty(user.getActivationCode()) && StringUtils.isEmpty(user.getNewEmail()))) {
            model.addAttribute("alert", "This address is not verified or not associated with account");
            model.addAttribute("alertType", "danger");
            return "forgotPassword";
        }

        userService.resetPassword(user);

        model.addAttribute("alert", "Check your email for a link to reset your password");
        model.addAttribute("alertType", "primary");

        return "login";
    }

    @GetMapping("/reset/{code}")
    public String resetCode(
            @PathVariable String code,
            Model model)
    {
        User user = userService.findByActivationCode(code);

        if (user == null) {
            model.addAttribute("alert", "Reset code is not found");
            model.addAttribute("alertType", "danger");
            return "login";
        }

        model.addAttribute("user", user);

        System.out.println(user);

        return "newPassword";
    }

    @PostMapping("/reset")
    public String updatePassword(
            @RequestParam("userId") User user,
            @RequestParam String newPassword,
            @RequestParam String passwordConfirm,
            Model model)
    {
        Map<String, String> errors = new HashMap<>();

        if (newPassword.length() < 5) {
            errors.put("newPasswordError", "Password should contain at least 5 characters");
        }

        if (!newPassword.equals(passwordConfirm)) {
            errors.put("passwordConfirmError", "Passwords are different");
        }

        if (errors.size() > 0) {
            model.addAttribute("user", user);
            model.mergeAttributes(errors);
            return "newPassword";
        }

        boolean isUpdated = userService.updateUser(user, newPassword, user.getEmail());

        if (!isUpdated) {
            model.addAttribute("alert", "Something went wrong. Please, try again");
            model.addAttribute("alertType", "danger");
            return "newPassword";
        }

        model.addAttribute("alert", "Password is updated");
        model.addAttribute("alertType", "success");

        return "login";
    }

}
