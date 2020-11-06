package com.example.sweater.controller;

import com.example.sweater.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GreetingController {

    @GetMapping
    public String greeting(
            @AuthenticationPrincipal User user,
            Model model
    ) {

        model.addAttribute("username", user != null ? user.getUsername() : "stranger");

        return "greeting";
    }


}