package com.example.sweater.service;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;
    @Value(value = "${upload.path}")
    private String uploadPath;

    public void saveFile(
            Message message,
            MultipartFile file
    ) throws IOException {

        if (file == null || file.getOriginalFilename().isEmpty()) {
            return;
        }

        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String resultFilename = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + "/" + resultFilename));

        message.setFilename(resultFilename);
    }

    public Page<Message> findAll(Pageable pageable) {
        return messageRepo.findAll(pageable);
    }

    public void saveMessage(Message message) {
        messageRepo.save(message);
    }

    public Page<Message> findByTag(String tag, Pageable pageable) {
        return messageRepo.findByTag(tag, pageable);
    }

    public Page<Message> findSubscriptionMessages(User user, Pageable pageable) {

        Page<Message> messages = new PageImpl<Message>(messageRepo.findAll(pageable)
                .filter(message -> message.getAuthor().getSubscribers().contains(user))
                .toList());

        return messages;
    }

    public void deleteMessage(Message message) {
        messageRepo.delete(message);
    }
}
