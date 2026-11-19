package com.example.springaizerotohero.dto;

import jakarta.validation.constraints.NotBlank;

public record PromptRequest(
        @NotBlank String message,
        String name,
        String voice,
        String adjective,
        String topic) {
}
