package com.example.springaizerotohero.dto;

import java.util.List;

public record StructuredAnswer(String topic, String summary, List<String> keyPoints) {
}
