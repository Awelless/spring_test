package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, value = 50) Pageable pageable,
            Model model
    ) {
        Page<Message> page;

        if (filter != null && !filter.isEmpty()) {
            page = messageService.findByTag(filter, pageable);
        } else {
            page = messageService.findAll(pageable);
        }

        model.addAttribute("page", page);
        model.addAttribute("url", "/messages");
        model.addAttribute("filter", filter);

        return "messages";
    }

    @PostMapping("/messages")
    public String addMessage(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, value = 50) Pageable pageable,
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("message", message);
            model.addAttribute("page", messageService.findAll(pageable));
            return "messages";
        }

        messageService.saveFile(message, file);
        messageService.saveMessage(message);

        return "redirect:/messages";
    }

    @GetMapping("/news")
    public String getNews(
            @AuthenticationPrincipal User user,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, value = 50) Pageable pageable,
            Model model
    ) {

        Page<Message> page = messageService.findSubscriptionMessages(user, pageable);

        model.addAttribute("page", page);
        model.addAttribute("url", "/news");

        return "news";
    }
}