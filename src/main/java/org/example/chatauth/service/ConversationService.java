package org.example.chatauth.service;

import lombok.RequiredArgsConstructor;
import org.example.chatauth.dto.ConversationDto;
import org.example.chatauth.entity.Conversation;
import org.example.chatauth.repository.ConversationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService {
    private final ConversationRepository repository;
    public Conversation getOrCreate(String user1, String user2) {

        return repository.findByUsers(user1, user2)
            .orElseGet(() -> {
                Conversation c = new Conversation();
                c.setUser1(user1);
                c.setUser2(user2);
                c.setLastMessageTime(LocalDateTime.now());
                return repository.save(c);
            });
    }

    // ذخیره یا آپدیت conversation
    public Conversation save(Conversation conversation) {
        return repository.save(conversation);
    }

    public List<ConversationDto> getUserChats(String email) {

        List<Conversation> conversations =
            repository.findAllByUser(email);

        return conversations.stream().map(c -> {

            ConversationDto dto = new ConversationDto();

            // پیدا کردن طرف مقابل
            if (c.getUser1().equals(email)) {
                dto.setFriendEmail(c.getUser2());
            } else {
                dto.setFriendEmail(c.getUser1());
            }

            // گرفتن آخرین پیام (فعلاً ساده فرضی)
            dto.setLastMessage("..."); // بعداً از Message table میگیری

            return dto;

        }).toList();
    }
}
