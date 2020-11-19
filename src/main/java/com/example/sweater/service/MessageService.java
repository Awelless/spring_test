package com.example.sweater.service;

import com.cloudinary.Cloudinary;
import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.domain.dto.MessageDto;
import com.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.UUID;
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

    public Page<MessageDto> findAll(User user, Pageable pageable) {
        return messageRepo.findAll(user, pageable);
    }

    public void saveMessage(Message message) {
        messageRepo.save(message);
    }

    public Page<MessageDto> findByTag(String filter, User user, Pageable pageable) {
        if (StringUtils.isEmpty(filter)) {
            return messageRepo.findAll(user, pageable);
        }

        Page<MessageDto> messages = new PageImpl<>(messageRepo.findByTag(filter, user, pageable).stream()
            .sorted(new Comparator<MessageDto>() {
                @Override
                public int compare(MessageDto a, MessageDto b) {
                    return (int) (b.getLikes() - a.getLikes());
                }
            })
            .collect(Collectors.toList()));

        return messages;
    }

    public Page<MessageDto> findSubscriptionMessages(User user, Pageable pageable) {

        Page<MessageDto> messages = new PageImpl<>(messageRepo.findAll(user, pageable)
                .filter(message -> message.getAuthor().getSubscribers().contains(user))
                .toList());

        return messages;
    }

    public void deleteMessage(Message message) {
        messageRepo.delete(message);
    }

    public Page<MessageDto> findByUser(User author, User user, Pageable pageable) {
        return messageRepo.findByUser(author, user, pageable);
    }

    public Page<MessageDto> findByPattern(String pattern, User user, Pageable pageable) {
        Page<MessageDto> messages = new PageImpl<>(messageRepo.findAll(user, pageable).stream()
                .filter(message -> message.getText().contains(pattern))
                .sorted(new Comparator<MessageDto>() {
                    @Override
                    public int compare(MessageDto a, MessageDto b) {
                        return (int) (b.getLikes() - a.getLikes());
                    }
                })
                .collect(Collectors.toList()));

        return  messages;
    }

}
