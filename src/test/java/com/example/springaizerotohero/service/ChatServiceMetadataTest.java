package com.example.springaizerotohero.service;

import com.example.springaizerotohero.dto.ChatMetadataResponse;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.EmptyRateLimit;
import org.springframework.ai.chat.metadata.RateLimit;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ChatServiceMetadataTest {

    @Test
    void returnsResponseUsageAndProviderRateLimit() {
        Usage usage = mock(Usage.class);
        when(usage.getPromptTokens()).thenReturn(12);
        when(usage.getCompletionTokens()).thenReturn(8);
        when(usage.getTotalTokens()).thenReturn(20);
        when(usage.getCacheReadInputTokens()).thenReturn(4L);
        when(usage.getCacheWriteInputTokens()).thenReturn(2L);

        RateLimit rateLimit = mock(RateLimit.class);
        when(rateLimit.getRequestsLimit()).thenReturn(100L);
        when(rateLimit.getRequestsRemaining()).thenReturn(75L);
        when(rateLimit.getRequestsReset()).thenReturn(Duration.ofMinutes(5));
        when(rateLimit.getTokensLimit()).thenReturn(10_000L);
        when(rateLimit.getTokensRemaining()).thenReturn(8_000L);
        when(rateLimit.getTokensReset()).thenReturn(Duration.ofMinutes(5));

        ChatMetadataResponse result = serviceWith(metadata("response-id", "test-model", usage, rateLimit))
                .chatWithMetadata("question");

        assertEquals("answer", result.answer());
        assertEquals("response-id", result.id());
        assertEquals("test-model", result.model());
        assertEquals(new ChatMetadataResponse.ChatUsage(12, 8, 20, 4L, 2L), result.usage());
        assertEquals(new ChatMetadataResponse.ChatRateLimit(
                100L, 75L, Duration.ofMinutes(5), 10_000L, 8_000L, Duration.ofMinutes(5)),
                result.rateLimit());
    }

    @Test
    void reportsUnavailableUsageAndRateLimitAsNull() {
        ChatMetadataResponse result = serviceWith(metadata(
                "response-id", "ollama-model", null, new EmptyRateLimit()))
                .chatWithMetadata("question");

        assertNull(result.usage());
        assertNull(result.rateLimit());
    }

    private ChatService serviceWith(ChatResponseMetadata metadata) {
        ChatClient chatClient = mock(ChatClient.class);
        ChatClient.ChatClientRequestSpec request = mock(ChatClient.ChatClientRequestSpec.class);
        ChatClient.CallResponseSpec call = mock(ChatClient.CallResponseSpec.class);

        when(chatClient.prompt()).thenReturn(request);
        when(request.user("question")).thenReturn(request);
        when(request.call()).thenReturn(call);
        when(call.chatResponse()).thenReturn(new ChatResponse(
                List.of(new Generation(new AssistantMessage("answer"))),
                metadata));

        return new ChatService(chatClient, mock(ChatClient.class));
    }

    private ChatResponseMetadata metadata(String id, String model, Usage usage, RateLimit rateLimit) {
        ChatResponseMetadata metadata = mock(ChatResponseMetadata.class);
        when(metadata.getId()).thenReturn(id);
        when(metadata.getModel()).thenReturn(model);
        when(metadata.getUsage()).thenReturn(usage);
        when(metadata.getRateLimit()).thenReturn(rateLimit);
        return metadata;
    }
}
