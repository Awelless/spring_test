package com.example.sweater.controller;

import com.example.sweater.domain.User;
import com.example.sweater.service.MessageService;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/search")
    public String search(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false, defaultValue = "") String pattern,
            @RequestParam(required = false, defaultValue = "user") String type,
            Pageable pageable,
            Model model
    ) throws Exception {

        model.addAttribute("pattern", pattern);

        if ("message".equals(type)) {
            model.addAttribute("isMessageList", true);
            model.addAttribute("page", messageService.findByPattern(pattern, pageable));
        } else {
            model.addAttribute("isUserList", true);
            model.addAttribute("users", userService.findByPattern(pattern, pageable));
        }

        return "searchList";
    }
}
