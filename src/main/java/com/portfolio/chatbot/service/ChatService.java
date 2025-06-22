package com.portfolio.chatbot.service;

import com.portfolio.chatbot.model.ChatMessage;
import com.portfolio.chatbot.repository.ChatMessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatMessageRepository chatRepo;
    private final MistralApiService mistralService;
    @Transactional
    public ChatMessage processUserMessage(String sessionId, String userMessage) {
        // Save user message
        ChatMessage userMsg = new ChatMessage();
        userMsg.setSessionId(sessionId);
        userMsg.setContent(userMessage);
        userMsg.setRole("user");
        chatRepo.save(userMsg);
        // Get AI response
        String aiResponse = mistralService.chatCompletion(userMessage).block();
        // Save AI response
        ChatMessage aiMsg = new ChatMessage();
        aiMsg.setSessionId(sessionId);
        aiMsg.setContent(aiResponse);
        aiMsg.setRole("assistant");
        return chatRepo.save(aiMsg);
    }
    public List<ChatMessage> getChatHistory(String sessionId) {
        return chatRepo.findBySessionId(sessionId);
    }
}
