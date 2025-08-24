package com.portfolio.chatbot.controller;

import com.portfolio.chatbot.dto.ChatRequest;
import com.portfolio.chatbot.model.ChatMessage;
import com.portfolio.chatbot.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    @PostMapping
    public ResponseEntity<ChatMessage> handleMessage(
            @RequestBody ChatRequest request,
            @RequestHeader("X-Session-ID") String sessionId) {
        return ResponseEntity.ok(
            chatService.processUserMessage(sessionId, request.getMessage())
        );
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ChatMessage> handleMessage(
            @RequestPart("message") String message,
            @RequestPart("pdf") MultipartFile pdfFile,
            @RequestHeader("X-Session-ID") String sessionId) {

        // Process PDF file if needed
        // For example: extract text, metadata, etc.

        return ResponseEntity.ok(
                chatService.processUserMessage(sessionId, message, pdfFile)
        );
    }
    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getHistory(
            @RequestHeader("X-Session-ID") String sessionId) {
        return ResponseEntity.ok(
            chatService.getChatHistory(sessionId)
        );
    }
}
