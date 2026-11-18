package com.example.springaizerotohero.service;

import com.example.springaizerotohero.dto.StructuredAnswer;
import org.springframework.ai.chat.client.ChatClient;
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
                .entity(StructuredAnswer.class);
    }
}
