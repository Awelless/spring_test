package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin_panel")
public class AdminPanelController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "adminPanel";
    }

    @GetMapping("/{user}")
    public String userEditForm(
            @PathVariable User user,
            Model model
    ) {
        model.addAttribute("user", user);

        return "userEdit";
    }

    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam("userId") User user,
            Model model
    ) {
        userService.saveUser(user, username);

        model.addAttribute("users", userService.findAll());
        model.addAttribute("alert", "User " + username + " is updated");

        return "adminPanel";
    }

    @PostMapping("/delete")
    public String userDelete(
            @RequestParam("userId") User user,
            Model model
    ) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("alert", "User " + user.getUsername() + " is updated");

        userService.deleteUser(user);

        return "adminPanel";
    }
}
