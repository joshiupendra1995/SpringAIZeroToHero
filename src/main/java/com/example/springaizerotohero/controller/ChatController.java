package com.example.springaizerotohero.controller;

import java.util.List;
import java.util.Map;

import com.example.springaizerotohero.dto.ChatMetadataResponse;
import com.example.springaizerotohero.dto.ChatRequest;
import com.example.springaizerotohero.dto.ChatResponse;
import com.example.springaizerotohero.dto.PromptRequest;
import com.example.springaizerotohero.dto.StructuredAnswer;
import com.example.springaizerotohero.service.ChatService;
import com.example.springaizerotohero.service.PromptService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final PromptService promptService;

    public ChatController(ChatService chatService, PromptService promptService) {
        this.chatService = chatService;
        this.promptService = promptService;
    }

    @PostMapping
    public ChatResponse chat(@Valid @RequestBody ChatRequest request) {
        return new ChatResponse(chatService.chat(request.message()));
    }

    @PostMapping("/metadata")
    public ChatMetadataResponse chatWithMetadata(@Valid @RequestBody ChatRequest request) {
        return chatService.chatWithMetadata(request.message());
    }

    @PostMapping("/structured")
    public StructuredAnswer structuredChat(@Valid @RequestBody ChatRequest request) {
        return chatService.structuredChat(request.message());
    }

    @PostMapping("/structured/native")
    public StructuredAnswer structuredChatWithProviderOutput(@Valid @RequestBody ChatRequest request) {
        return chatService.structuredChatWithProviderOutput(request.message());
    }

    @PostMapping("/converters/bean")
    public StructuredAnswer chatWithBeanConverter(@Valid @RequestBody ChatRequest request) {
        return chatService.structuredChatWithConverter(request.message());
    }

    @PostMapping("/converters/list")
    public List<String> chatWithListConverter(@Valid @RequestBody ChatRequest request) {
        return chatService.listWithConverter(request.message());
    }

    @PostMapping("/converters/map")
    public Map<String, Object> chatWithMapConverter(@Valid @RequestBody ChatRequest request) {
        return chatService.mapWithConverter(request.message());
    }

    @PostMapping("/roles")
    public ChatResponse chatWithRoles(@Valid @RequestBody PromptRequest request) {
        String answer = promptService.chatWithRoles(request.message(), request.name(), request.voice());
        return new ChatResponse(answer);
    }

    @PostMapping("/template")
    public ChatResponse chatWithTemplate(@Valid @RequestBody PromptRequest request) {
        String answer = promptService.renderTemplate(request.adjective(), request.topic());
        return new ChatResponse(answer);
    }

    @PostMapping("/resource-template")
    public ChatResponse chatWithResourceTemplate(@Valid @RequestBody PromptRequest request) {
        String answer = promptService.useResourceTemplate(request.message(), request.name(), request.voice());
        return new ChatResponse(answer);
    }
}
