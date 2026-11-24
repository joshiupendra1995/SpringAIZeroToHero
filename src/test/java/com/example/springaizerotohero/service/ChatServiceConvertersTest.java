package com.example.springaizerotohero.service;

import java.util.List;
import java.util.Map;

import com.example.springaizerotohero.dto.StructuredAnswer;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ChatServiceConvertersTest {

    @Test
    void convertsBeanAndAddsSchemaInstructions() {
        ChatModel model = modelReturning("""
                Here is the answer:
                ```json
                {"topic":"AI","summary":"A framework","keyPoints":["models"]}
                ```
                """);

        assertEquals(new StructuredAnswer("AI", "A framework", List.of("models")),
                service(model).structuredChatWithConverter("Explain AI"));
        assertPromptContains(model, "Explain AI", "JSON Schema", "keyPoints");
    }

    @Test
    void convertsCommaDelimitedListAndAddsFormatInstructions() {
        ChatModel model = modelReturning("vanilla, chocolate, strawberry");

        assertEquals(List.of("vanilla", "chocolate", "strawberry"),
                service(model).listWithConverter("List three flavors"));
        assertPromptContains(model, "List three flavors", "comma");
    }

    @Test
    void convertsMapAndAddsJsonInstructions() {
        ChatModel model = modelReturning("{\"numbers\":[1,2,3]}");

        assertEquals(Map.of("numbers", List.of(1, 2, 3)),
                service(model).mapWithConverter("List numbers"));
        assertPromptContains(model, "List numbers", "JSON");
    }

    private ChatModel modelReturning(String text) {
        ChatModel model = mock(ChatModel.class);
        when(model.getOptions()).thenReturn(ChatOptions.builder().build());
        when(model.call(any(Prompt.class)))
                .thenReturn(new ChatResponse(List.of(new Generation(new AssistantMessage(text)))));
        return model;
    }

    private ChatService service(ChatModel model) {
        ChatClient client = ChatClient.create(model);
        return new ChatService(client, client);
    }

    private void assertPromptContains(ChatModel model, String... fragments) {
        var prompt = ArgumentCaptor.forClass(Prompt.class);
        verify(model).call(prompt.capture());
        String text = prompt.getValue().getContents();
        for (String fragment : fragments) {
            assertTrue(text.contains(fragment), () -> "Expected prompt to contain: " + fragment);
        }
    }
}
