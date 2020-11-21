package com.example.sweater.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    static List<String> getTags(String source) {
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(source);

        List<String> tags = new ArrayList<>();

        while (matcher.find()) {
            tags.add(
                    source.substring(
                            matcher.start(), matcher.end()
                    )
            );
        }

        return tags;
    }
}
