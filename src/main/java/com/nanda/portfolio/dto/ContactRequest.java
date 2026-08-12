package com.nanda.portfolio.dto;
import jakarta.validation.constraints.*;
public record ContactRequest(@NotBlank @Size(max=100) String name,@NotBlank @Email @Size(max=180) String email,@Size(max=160) String subject,@NotBlank @Size(max=5000) String message) {}
