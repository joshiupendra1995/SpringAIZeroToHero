package com.example.springaizerotohero.service;

import com.example.springaizerotohero.dto.ChatMetadataResponse;
import com.example.springaizerotohero.dto.StructuredAnswer;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.EmptyRateLimit;
import org.springframework.ai.chat.metadata.RateLimit;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private final ChatClient structuredChatClient;

    public ChatService(
            ChatClient chatClient,
            @Qualifier("structuredChatClient") ChatClient structuredChatClient) {
        this.chatClient = chatClient;
        this.structuredChatClient = structuredChatClient;
    }

    public String chat(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    public ChatMetadataResponse chatWithMetadata(String message) {
        var response = chatClient.prompt()
                .user(message)
                .call()
                .chatResponse();

        ChatResponseMetadata metadata = response.getMetadata();
        Usage usage = metadata.getUsage();
        RateLimit rateLimit = metadata.getRateLimit();

        return new ChatMetadataResponse(
                response.getResult().getOutput().getText(),
                metadata.getId(),
                metadata.getModel(),
                new ChatMetadataResponse.ChatUsage(
                                        usage.getPromptTokens(),
                                        usage.getCompletionTokens(),
                                        usage.getTotalTokens(),
                                        usage.getCacheReadInputTokens(),
                                        usage.getCacheWriteInputTokens()),
                rateLimit instanceof EmptyRateLimit
                        ? null
                        : new ChatMetadataResponse.ChatRateLimit(
                                rateLimit.getRequestsLimit(),
                                rateLimit.getRequestsRemaining(),
                                rateLimit.getRequestsReset(),
                                rateLimit.getTokensLimit(),
                                rateLimit.getTokensRemaining(),
                                rateLimit.getTokensReset()));
    }

    public StructuredAnswer structuredChat(String message) {
        return structuredChatClient.prompt()
                .user("""
                        Answer the user's request as a JSON object with exactly these fields:
                        - topic: a short topic name
                        - summary: a concise answer
                        - keyPoints: a list of three concise points

                        User request: %s
                        """.formatted(message))
                .call()
                .entity(StructuredAnswer.class, ChatClient.EntityParamSpec::validateSchema);
    }
}
