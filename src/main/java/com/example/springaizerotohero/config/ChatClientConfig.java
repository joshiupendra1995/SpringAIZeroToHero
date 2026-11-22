package com.example.springaizerotohero.config;

import com.example.springaizerotohero.advisor.ReReadingAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("You are a helpful assistant. Give clear, concise answers.")
                .defaultAdvisors(new ReReadingAdvisor())
                .build();
    }

    @Bean
    ChatClient structuredChatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("You are a helpful assistant. Return accurate, concise structured answers.")
                .build();
    }
}
