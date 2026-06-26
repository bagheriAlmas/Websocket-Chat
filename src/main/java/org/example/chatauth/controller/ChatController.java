package org.example.chatauth.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatauth.dto.ChatRequest;
import org.example.chatauth.entity.ChatMessage;
import org.example.chatauth.entity.Conversation;
import org.example.chatauth.repository.ChatMessageRepository;
import org.example.chatauth.service.ConversationService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageRepository messageRepository;
    private final ConversationService conversationService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.send")
    public void send(ChatRequest request, Principal principal) {

        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String sender = principal.getName();
        String receiver = request.receiverEmail();

        // 1. گرفتن یا ساخت conversation
        Conversation conversation =
            conversationService.getOrCreate(sender, receiver);

        // 2. ساخت پیام
        ChatMessage message = new ChatMessage();
        message.setConversationId(conversation.getId());
        message.setSenderEmail(sender);
        message.setReceiverEmail(receiver);
        message.setContent(request.content());
        message.setTimestamp(LocalDateTime.now());

        // 3. ذخیره پیام در دیتابیس
        messageRepository.save(message);

        // 4. آپدیت last message conversation (برای inbox)
        conversation.setLastMessageTime(LocalDateTime.now());
        conversationService.save(conversation);

        // 5. ارسال real-time به گیرنده
        messagingTemplate.convertAndSendToUser(
            receiver,
            "/queue/messages",
            message
        );
    }
}
