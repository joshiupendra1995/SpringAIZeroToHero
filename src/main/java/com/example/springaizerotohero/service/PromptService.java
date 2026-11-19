package com.example.springaizerotohero.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PromptService {

    private final ChatClient chatClient;
    private final Resource systemPromptResource;

    public PromptService(ChatClient chatClient,
                         @Value("classpath:/prompts/system-message.st") Resource systemPromptResource) {
        this.chatClient = chatClient;
        this.systemPromptResource = systemPromptResource;
    }

    public String chatWithRoles(String message, String name, String voice) {
        List<Message> messages = List.of(
                new SystemMessage("You are " + fallback(name, "SpringAI Mentor")
                        + ", a concise technical mentor. Reply in the voice of "
                        + fallback(voice, "a patient teacher") + "."),
                new UserMessage(message)
        );

        return chatClient.prompt()
                .messages(messages)
                .call()
                .content();
    }

    public String renderTemplate(String adjective, String topic) {
        PromptTemplate template = PromptTemplate.builder()
                .template("Tell me a {adjective} joke about {topic}")
                .build();

        String prompt = template.render(Map.of(
                "adjective", fallback(adjective, "clever"),
                "topic", fallback(topic, "Spring AI")
        ));
        return chatClient.prompt(prompt).call().content();
    }

    public String useResourceTemplate(String message, String name, String voice) {
        SystemPromptTemplate template = new SystemPromptTemplate(systemPromptResource);
        Message systemMessage = template.createMessage(Map.of(
                "name", fallback(name, "SpringAI Mentor"),
                "voice", fallback(voice, "a patient teacher")
        ));

        return chatClient.prompt()
                .messages(systemMessage, new UserMessage(message))
                .call()
                .content();
    }

    private String fallback(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value.trim();
    }
}
