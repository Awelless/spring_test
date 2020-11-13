package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.MessageDto;
import com.example.sweater.service.MessageService;
import com.example.sweater.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/{user}")
    public String userMessages(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            @RequestParam(required = false) Message message,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, value = 50) Pageable pageable,
            Model model
    ) {

        Page<MessageDto> page = messageService.findByUser(currentUser, user, pageable);

        model.addAttribute("page", page);
        model.addAttribute("url", "/" + user.getId());
        model.addAttribute("message", message);
        model.addAttribute("userPage", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("isCurrentUser", currentUser.equals(user));

        return "userMessages";
    }

    @PostMapping("/{user}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable("user") Long userId,
            @RequestParam("messageId") Message message,
            @RequestParam("text") String newText,
            @RequestParam("tag") String newTag,
            @RequestParam("file") MultipartFile newFile
    ) throws IOException {

        if (currentUser.getRoles().contains(Role.ADMIN) || message.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(newText)) {
                message.setText(newText);
            }
            if (!StringUtils.isEmpty(newTag)) {
                message.setTag(newTag);
            }

            messageService.saveFile(message, newFile);
            messageService.saveMessage(message);
        }

        return "redirect:/user/" + userId;
    }

    @PostMapping("/{user}/{message}/delete")
    public String deleteMessage(
            @PathVariable("user") Long userId,
            @PathVariable Message message
    ) {
        messageService.deleteMessage(message);

        return "redirect:/user/" + userId;
    }

    @GetMapping("/{user}/subscribe")
    public String subscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        userService.subscribe(currentUser, user);

        return "redirect:/user/" + user.getId();
    }

    @GetMapping("/{user}/unsubscribe")
    public String unsubscribe(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user
    ) {
        userService.unsubscribe(currentUser, user);

        return "redirect:/user/" + user.getId();
    }

    @GetMapping("/{user}/{pageType}")
    public String userList(
            @PathVariable User user,
            @PathVariable String pageType,
            Model model
    ) {
        model.addAttribute("userPage", user);
        model.addAttribute("pageType", pageType);

        if (pageType.equals("subscriptions")) {
            model.addAttribute("users", user.getSubscriptions());
        } else {
            model.addAttribute("users", user.getSubscribers());
        }

        return "subs";
    }
}
