package com.example.springaizerotohero.controller;

import java.util.List;
import java.util.Map;

import com.example.springaizerotohero.dto.StructuredAnswer;
import com.example.springaizerotohero.service.ChatService;
import com.example.springaizerotohero.service.PromptService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ChatControllerConvertersTest {

    @Test
    void routesAndSerializesConverterResponses() throws Exception {
        ChatService service = mock(ChatService.class);
        MockMvc mvc = MockMvcBuilders.standaloneSetup(new ChatController(service, mock(PromptService.class))).build();
        when(service.structuredChatWithConverter("question"))
                .thenReturn(new StructuredAnswer("AI", "Summary", List.of("point")));
        when(service.listWithConverter("question")).thenReturn(List.of("one", "two"));
        when(service.mapWithConverter("question")).thenReturn(Map.of("numbers", List.of(1, 2)));

        mvc.perform(post("/api/chat/converters/bean")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"message\":\"question\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"topic\":\"AI\",\"summary\":\"Summary\",\"keyPoints\":[\"point\"]}"));
        mvc.perform(post("/api/chat/converters/list")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"message\":\"question\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"one\",\"two\"]"));
        mvc.perform(post("/api/chat/converters/map")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"message\":\"question\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"numbers\":[1,2]}"));

        verify(service).structuredChatWithConverter("question");
        verify(service).listWithConverter("question");
        verify(service).mapWithConverter("question");

        mvc.perform(post("/api/chat/converters/bean")
                        .contentType(MediaType.APPLICATION_JSON).content("{\"message\":\" \"}"))
                .andExpect(status().isBadRequest());
    }
}
