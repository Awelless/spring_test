package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MessageListController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public String getMessages(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model
    ) {

        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty()) {
            messages = messageService.findByTag(filter);
        } else {
            messages = messageService.findAll();
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

        messageService.saveFile(message, file);
        messageService.saveMessage(message);

        return "redirect:/messages";
    }

    @GetMapping("/news")
    public String getNews(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        List<Message> messages = (List<Message>) messageService.findAll();

        messages = messages.stream()
                    .filter(curMessage ->
                            curMessage.getAuthor().getSubscribers().contains(user))
                    .collect(Collectors.toList());

        model.addAttribute("messages", messages);

        return "news";
    }
}