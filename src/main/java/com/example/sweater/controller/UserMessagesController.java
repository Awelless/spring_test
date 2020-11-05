package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Controller
@RequestMapping("/user-messages")
public class UserMessagesController {
    @Autowired
    private MessageRepo messageRepo;
    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/{user}")
    public String userMessages(
            @AuthenticationPrincipal User curUser,
            @PathVariable User user,
            @RequestParam(required = false) Message message,
            Model model
    ) {
        Set<Message> messages = user.getMessages();

        model.addAttribute("messages", messages);
        model.addAttribute("message", message);
        model.addAttribute("userPage", user);
        model.addAttribute("isCurrentUser", curUser.equals(user));

        return "userMessages";
    }

    @PostMapping("/{user}")
    public String updateMessage(
            @AuthenticationPrincipal User curUser,
            @PathVariable("user") Long userId,
            @RequestParam("messageId") Message message,
            @RequestParam("text") String newText,
            @RequestParam("tag") String newTag,
            @RequestParam("file") MultipartFile newFile
    ) throws IOException {

        if (message.getAuthor().equals(curUser)) {
            if (!StringUtils.isEmpty(newText)) {
                message.setText(newText);
            }
            if (!StringUtils.isEmpty(newTag)) {
                message.setTag(newTag);
            }

            ControllerUtils.saveFile(message, newFile, uploadPath);

            messageRepo.save(message);
        }

        return "redirect:/user-messages/" + userId;
    }
}
