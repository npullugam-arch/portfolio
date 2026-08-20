package com.nanda.portfolio.config;

import java.net.http.HttpClient;
import java.time.Duration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = false)
@EnableConfigurationProperties(OpenRouterProperties.class)
public class ChatConfig {
    private static final Logger log = LoggerFactory.getLogger(ChatConfig.class);

    @Bean
    RestClient openRouterRestClient(RestClient.Builder builder, OpenRouterProperties properties, @Value("${openrouter.api.key:}") String apiKey) {
        log.info("OpenRouter key configured: {}, model: {}, endpoint: {}",
            apiKey != null && !apiKey.isBlank(), properties.model(), properties.endpoint());
        HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(properties.connectTimeoutSeconds()))
            .build();
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);
        requestFactory.setReadTimeout(Duration.ofSeconds(properties.readTimeoutSeconds()));
        return builder
            .requestFactory(requestFactory)
            .baseUrl(properties.endpoint())
            .defaultHeader("Accept", "application/json")
            .defaultHeader("HTTP-Referer", "https://localhost")
            .defaultHeader("X-Title", "Portfolio CMS")
            .build();
    }
}