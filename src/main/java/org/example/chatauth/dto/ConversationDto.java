package org.example.chatauth.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConversationDto {

    private Long conversationId;

    private String friendEmail;

    private String lastMessage;

    private LocalDateTime lastMessageTime;

}
