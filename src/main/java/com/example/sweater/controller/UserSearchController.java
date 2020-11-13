package com.example.sweater.controller;

import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserSearchController {
    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public String searchUsers(
            @RequestParam(required = false, defaultValue = "") String pattern,
            Model model
    ) {
        model.addAttribute("pattern", pattern);
        model.addAttribute("users", userService.findByPattern(pattern));

        return "userList";
    }
}
