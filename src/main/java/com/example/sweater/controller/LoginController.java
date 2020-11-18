package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        //send message

        model.addAttribute("alert", "Check your email for a link to reset your password");
        model.addAttribute("alertType", "primary");

        return "login";
    }
}
