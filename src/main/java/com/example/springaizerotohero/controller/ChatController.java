package com.example.springaizerotohero.controller;

import com.example.springaizerotohero.dto.ChatRequest;
import com.example.springaizerotohero.dto.ChatResponse;
import com.example.springaizerotohero.dto.StructuredAnswer;
import com.example.springaizerotohero.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ChatResponse chat(@Valid @RequestBody ChatRequest request) {
        return new ChatResponse(chatService.chat(request.message()));
    }

    @PostMapping("/structured")
    public StructuredAnswer structuredChat(@Valid @RequestBody ChatRequest request) {
        return chatService.structuredChat(request.message());
    }
}
