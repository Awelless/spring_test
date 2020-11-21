package com.example.sweater.service;

import com.cloudinary.Cloudinary;
import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private Cloudinary cloudinary;
    @Value(value = "${upload.path}")
    private String uploadPath;

    public void saveFile(
            Message message,
            MultipartFile file
    ) throws IOException {

        if (file == null || file.getOriginalFilename().isEmpty()) {
            return;
        }

        Map uploadResponse = cloudinary.uploader().upload(file.getBytes(), Collections.emptyMap());

        message.setFilename(uploadResponse.get("public_id").toString() + "." + uploadResponse.get("format").toString());
        message.setNormalFilename(file.getOriginalFilename());
    }

    public Page<Message> findAll(Pageable pageable) {
        return messageRepo.findAll(pageable);
    }

    public void saveMessage(Message message) {
        messageRepo.save(message);
    }

    public Page<Message> findByTag(String filter, Pageable pageable) {
        if (StringUtils.isEmpty(filter)) {
            return messageRepo.findAll(pageable);
        }

        Page<Message> messages = new PageImpl<>(messageRepo.findAll(pageable).stream()
                .filter(message -> message.getTags().contains(filter))
                .sorted(new Comparator<Message>() {
                    @Override
                    public int compare(Message a, Message b) {
                        return (int) (b.getLikes().size() - a.getLikes().size());
                    }
                })
                .collect(Collectors.toList()));

        return messages;
    }

    public Page<Message> findSubscriptionMessages(User user, Pageable pageable) {

        Page<Message> messages = new PageImpl<>(messageRepo.findAll(pageable)
                .filter(message -> message.getAuthor().getSubscribers().contains(user))
                .toList());

        return messages;
    }

    public void deleteMessage(Message message) {
        messageRepo.delete(message);
    }

    public Page<Message> findByAuthor(User author, Pageable pageable) {
        return messageRepo.findByAuthor(author, pageable);
    }

    public Page<Message> findByPattern(String pattern, Pageable pageable) {
        Page<Message> messages = new PageImpl<>(messageRepo.findAll(pageable).stream()
                .filter(message -> message.getText().contains(pattern))
                .sorted(new Comparator<Message>() {
                    @Override
                    public int compare(Message a, Message b) {
                        return (int) (b.getLikes().size() - a.getLikes().size());
                    }
                })
                .collect(Collectors.toList()));

        return  messages;
    }

}
