package com.nanda.portfolio.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "openrouter")
public record OpenRouterProperties(
    String apiKey,
    String model,
    String endpoint,
    int connectTimeoutSeconds,
    int readTimeoutSeconds) {
    public OpenRouterProperties {
        model = model == null || model.isBlank() ? "nvidia/nemotron-3-ultra-550b-a55b:free" : model;
        endpoint = endpoint == null || endpoint.isBlank() ? "https://openrouter.ai/api/v1" : endpoint;
        connectTimeoutSeconds = connectTimeoutSeconds > 0 ? connectTimeoutSeconds : 5;
        readTimeoutSeconds = readTimeoutSeconds > 0 ? readTimeoutSeconds : 60;
    }
}