package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repos.MessageRepo;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {

    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        Map<String, String> errorsMap = bindingResult.getFieldErrors().stream().collect(collector);
        return errorsMap;
    }

    static void saveFile(
            Message message,
            MultipartFile file,
            @NonNull String uploadPath
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
}
