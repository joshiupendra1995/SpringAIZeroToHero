package com.example.springaizerotohero.advisor;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReReadingAdvisorTest {

    @Test
    void repeatsUserQuestionInPrompt() {
        String question = "What does a Spring AI advisor do?";
        ChatClientRequest request = ChatClientRequest.builder()
                .prompt(new Prompt(new UserMessage(question)))
                .build();

        ChatClientRequest advisedRequest = new ReReadingAdvisor().before(request, new AdvisorChain() { });

        assertEquals("""
                What does a Spring AI advisor do?
                Read the question again: What does a Spring AI advisor do?
                """, advisedRequest.prompt().getUserMessage().getText());
    }
}
