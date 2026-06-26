package org.example.chatauth.repository;


import org.example.chatauth.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    // پیدا کردن conversation بین دو کاربر (بدون توجه به ترتیب)
    @Query("""
        SELECT c FROM Conversation c
        WHERE (c.user1 = :user1 AND c.user2 = :user2)
           OR (c.user1 = :user2 AND c.user2 = :user1)
    """)
    Optional<Conversation> findByUsers(
        @Param("user1") String user1,
        @Param("user2") String user2
    );

    // گرفتن همه چت‌های یک کاربر (لیست چت‌ها مثل WhatsApp)
    @Query("""
        SELECT c FROM Conversation c
        WHERE c.user1 = :email OR c.user2 = :email
        ORDER BY c.lastMessageTime DESC
    """)
    List<Conversation> findAllByUser(
        @Param("email") String email
    );
}
