package com.example.sweater.service;

import com.example.sweater.domain.Message;
import com.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    public Iterable<Message> findAll() {
        return messageRepo.findAll();
    }

    public void saveMessage(Message message) {
        messageRepo.save(message);
    }

    public List<Message> findByTag(String tag) {
        return messageRepo.findByTag(tag);
    }

    public void deleteMessage(Message message) {
        messageRepo.delete(message);
    }
}
