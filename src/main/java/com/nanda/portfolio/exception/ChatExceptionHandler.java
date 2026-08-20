package com.nanda.portfolio.exception;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = com.nanda.portfolio.controller.ChatController.class)
public class ChatExceptionHandler {
    @ExceptionHandler(ChatException.class)
    ResponseEntity<Map<String, String>> chat(ChatException exception) {
        ResponseEntity.BodyBuilder response = ResponseEntity.status(exception.status());
        if (exception.retryAfterSeconds() != null) response.header("Retry-After", String.valueOf(exception.retryAfterSeconds()));
        return response.body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String, String>> invalid() {
        return ResponseEntity.badRequest().body(Map.of("error", "Please enter a message of 1 to 4000 characters."));
    }
}