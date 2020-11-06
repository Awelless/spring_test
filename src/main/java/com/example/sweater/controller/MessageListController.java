package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

@Controller
public class MessageListController {
    @Autowired
    private MessageRepo messageRepo;
    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/messages")
    public String getMessages(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {

        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "messages";
    }

    @PostMapping("/messages")
    public String addMessage(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("message", message);
            return "messages";
        }

        ControllerUtils.saveFile(message, file, uploadPath);
        messageRepo.save(message);

        return "redirect:/messages";
    }

    @GetMapping("/news")
    public String getNews(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        /*
         * failed to lazily initialize a collection of role: com.example.sweater.domain.User.messages, could not initialize proxy - no Session
         */
        //model.addAttribute("messages", user.getMessages());

        //realise with stream api
        //model.addAttribute("messages", user.getSubscriptionsMessages());

        model.addAttribute("notWorking", true);

        return "news";
    }
}