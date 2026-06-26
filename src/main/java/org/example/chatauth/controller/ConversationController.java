package org.example.chatauth.controller;

import lombok.RequiredArgsConstructor;
import org.example.chatauth.dto.ConversationDto;
import org.example.chatauth.service.ConversationService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @GetMapping
    public List<ConversationDto> getChats(Principal principal) {
        return conversationService.getUserChats(principal.getName());
    }
}
