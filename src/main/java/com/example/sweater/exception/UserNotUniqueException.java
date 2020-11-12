package com.example.sweater.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.TreeMap;

@Getter
@AllArgsConstructor
public class UserNotUniqueException extends RuntimeException {
    private Map<String, String> errors = new TreeMap<>();
}
