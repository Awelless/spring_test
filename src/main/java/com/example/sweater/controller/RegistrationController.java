package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.CaptchaResponseDto;
import com.example.sweater.exception.UserNotUniqueException;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    private final static String CAPTCHA_URL =
            "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;

    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/registration")
    public String registration(
            @AuthenticationPrincipal User currentUser
    ) {
        if (currentUser != null) {
            return "redirect:/";
        }

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @RequestParam("g-recaptcha-response") String captchaResponse,
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

        boolean isUnique = true;
        if (!ControllerUtils.getErrors(bindingResult).containsKey("usernameError") &&
                !ControllerUtils.getErrors(bindingResult).containsKey("emailError")) {
            try {
                userService.addUser(user);
            } catch (UserNotUniqueException e) {
                isUnique = false;
                model.mergeAttributes(e.getErrors());
            }
        }

        if (isConfirmEmpty || !isPasswordsEqual || !isUnique || bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            for (String key : errors.keySet()) {
                System.out.println(key + " " + errors.get(key));
            }
            model.mergeAttributes(errors);
            return "registration";
        }

        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "Some troubles with captcha. Try again");
            return "registration";
        }

        model.addAttribute("alert", "Activation code is sent to your e-mail");
        model.addAttribute("alertType", "primary");

        return "login";
    }

    @GetMapping("activate/{code}")
    public String activate(
            @PathVariable String code,
            Model model) {

        User user = userService.activateUser(code);

        if (user != null) {
            model.addAttribute("alert", "User " + user.getUsername() + " is activated");
            model.addAttribute("alertType", "success");
        } else {
            model.addAttribute("alert", "Activation code is not found");
            model.addAttribute("alertType", "danger");
        }

        return "login";
    }
}
