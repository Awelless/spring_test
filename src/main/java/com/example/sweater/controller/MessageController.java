package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.MessageDto;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public String getMessages(
            @AuthenticationPrincipal User currentUser,
            @RequestParam(required = false, defaultValue = "") String filter,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, value = 50) Pageable pageable,
            Model model
    ) {
        Page<MessageDto> page = messageService.findByTag(filter, currentUser, pageable);

        model.addAttribute("page", page);
        model.addAttribute("url", "/messages");
        model.addAttribute("filter", filter);

        return "messages";
    }

    @PostMapping("/messages")
    public String addMessage(
            @AuthenticationPrincipal User currentUser,
            @Valid Message message,
            BindingResult bindingResult,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, value = 50) Pageable pageable,
            Model model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        message.setAuthor(currentUser);

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("message", message);
            model.addAttribute("page", messageService.findAll(currentUser, pageable));
            return "messages";
        }

        messageService.saveFile(message, file);
        messageService.saveMessage(message);

        return "redirect:/messages";
    }

    @GetMapping("/news")
    public String getNews(
            @AuthenticationPrincipal User currentUser,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, value = 50) Pageable pageable,
            Model model
    ) {

        Page<MessageDto> page = messageService.findSubscriptionMessages(currentUser, pageable);

        model.addAttribute("page", page);
        model.addAttribute("url", "/news");

        return "news";
    }

    @GetMapping("/messages/{message}/like")
    public String like(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Message message,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer
    ) {
        Set<User> likes = message.getLikes();

        if (likes.contains(currentUser)) {
            likes.remove(currentUser);
        } else {
            likes.add(currentUser);
        }

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();
        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));

        return "redirect:" + components.getPath();
    }
}