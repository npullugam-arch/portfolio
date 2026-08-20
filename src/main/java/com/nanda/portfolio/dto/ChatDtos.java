package com.nanda.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public final class ChatDtos {
    private ChatDtos() { }

    public record ChatRequest(@NotBlank @Size(max = 4000) String message) { }
    public record ChatResponse(String reply) { }

    public record OpenRouterMessage(String role, String content) { }

    public record OpenRouterRequest(
        String model,
        List<OpenRouterMessage> messages,
        double temperature,
        boolean stream,
        int max_tokens) { }

    public record OpenRouterResponse(List<Choice> choices, Error error) {
        public record Choice(Message message) { }
        public record Message(String role, String content) { }
        public record Error(String message, String type, String code) { }
    }
}