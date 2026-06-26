package org.example.chatauth.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatauth.entity.ChatMessage;
import org.example.chatauth.service.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRestController {

    private final ChatService chatService;

    @GetMapping("/{conversationId}")
    public List<ChatMessage> getMessages(
        @PathVariable Long conversationId) {

        return chatService.getMessages(conversationId);
    }

}
