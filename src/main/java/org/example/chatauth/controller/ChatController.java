package org.example.chatauth.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatauth.dto.ChatRequest;
import org.example.chatauth.entity.ChatMessage;
import org.example.chatauth.repository.ChatMessageRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageRepository repository;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void send(ChatRequest request, Principal principal) {

        // 1. کنترل امنیت پایه
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String senderEmail = principal.getName();

        // 2. ساخت پیام
        ChatMessage message = new ChatMessage();
        message.setSenderEmail(senderEmail);
        message.setReceiverEmail(request.receiverEmail());
        message.setContent(request.content());
        message.setTimestamp(LocalDateTime.now());

        // 3. ذخیره در دیتابیس
        repository.save(message);

        // 4. ارسال پیام به کاربر مقصد
        messagingTemplate.convertAndSendToUser(
            request.receiverEmail(),
            "/queue/messages",
            message
        );
    }
}
