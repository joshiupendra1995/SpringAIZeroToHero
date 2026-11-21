package com.example.springaizerotohero.dto;

import java.time.Duration;

public record ChatMetadataResponse(
        String answer,
        String id,
        String model,
        ChatUsage usage,
        ChatRateLimit rateLimit) {

    public record ChatUsage(
            Integer promptTokens,
            Integer completionTokens,
            Integer totalTokens,
            Long cacheReadInputTokens,
            Long cacheWriteInputTokens) {
    }

    public record ChatRateLimit(
            Long requestsLimit,
            Long requestsRemaining,
            Duration requestsReset,
            Long tokensLimit,
            Long tokensRemaining,
            Duration tokensReset) {
    }
}
