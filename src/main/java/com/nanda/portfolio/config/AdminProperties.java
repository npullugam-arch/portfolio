package com.nanda.portfolio.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties("portfolio.admin")
public record AdminProperties(String username, String password, String rememberMeKey) {}
