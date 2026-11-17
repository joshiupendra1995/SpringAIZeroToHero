package com.example.springaizerotohero.advisor;

import org.jspecify.annotations.NullMarked;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;

public class ReReadingAdvisor implements BaseAdvisor {

    @Override
    public @NullMarked ChatClientRequest before(ChatClientRequest request, AdvisorChain advisorChain) {
        String question = request.prompt().getUserMessage().getText();
        String augmentedQuestion = """
                %s
                Read the question again: %s
                """.formatted(question, question);

        return request.mutate()
                .prompt(request.prompt().augmentUserMessage(augmentedQuestion))
                .build();
    }

    @Override
    public @NullMarked ChatClientResponse after(ChatClientResponse response, AdvisorChain advisorChain) {
        return response;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
