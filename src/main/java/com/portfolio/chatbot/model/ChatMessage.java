package com.portfolio.chatbot.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sessionId; // To track user sessions
    private String role; // "user" or "assistant"
    @Lob
    private String content;
    private LocalDateTime timestamp;
}
   