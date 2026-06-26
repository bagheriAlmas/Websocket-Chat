package org.example.chatauth.repository;

import org.example.chatauth.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findBySenderEmailAndReceiverEmail(
        String sender, String receiver
    );

    List<ChatMessage> findByConversationIdOrderByTimestampAsc(Long conversationId);
}
