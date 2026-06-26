package org.example.chatauth.service;

import lombok.RequiredArgsConstructor;
import org.example.chatauth.entity.ChatMessage;
import org.example.chatauth.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getMessages(Long conversationId) {
        return chatMessageRepository
            .findByConversationIdOrderByTimestampAsc(conversationId);
    }
}
