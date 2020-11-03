package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {
        boolean isPasswordsEqual = user.getPassword().equals(passwordConfirm);
        if (!isPasswordsEqual) {
            model.addAttribute("password2Error", "Passwords are different!");
        }

        boolean isConfirmEmpty = StringUtils.isEmpty(passwordConfirm);
        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Confirm the password");
        }

        if (isConfirmEmpty || !isPasswordsEqual || bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists");
            return "registration";
        }

        return "redirect:/login";
    }

    @GetMapping("activate/{code}")
    public String activate(
            @PathVariable String code,
            Model model) {

        String username = userService.activateUser(code);

        if (username != null) {
            model.addAttribute("activationSuccess", "User " + username + " is activated");
        } else {
            model.addAttribute("activationError", "Activation code is not found");
        }

        return "login";
    }
}
