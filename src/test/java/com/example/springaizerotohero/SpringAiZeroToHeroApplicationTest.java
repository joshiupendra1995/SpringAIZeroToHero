package com.example.springaizerotohero;

import com.example.springaizerotohero.controller.ChatController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SpringAiZeroToHeroApplicationTest {

    @Autowired
    private ChatController chatController;

    @Test
    void applicationContextLoadsWithStructuredChatClient() {
        assertNotNull(chatController);
    }
}
