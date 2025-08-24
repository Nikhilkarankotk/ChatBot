package com.portfolio.chatbot.service.RAG;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextChunker {
//    public List<String> chunkText(String text, int chunkSize) {
//        List<String> chunks = new ArrayList<>();
//        int length = text.length();
//        if(chunkSize <= 0) {
//            throw new IllegalArgumentException("Chunk size must be positive.");
//        }
//        for (int i = 0; i < length; i += chunkSize) {
//            chunks.add(text.substring(i, Math.min(length, i + chunkSize)));
//        }
//        return chunks;
//    }
//
    public List<String> chunkText(String text, int chunkSize) {
        List<String> chunks = new ArrayList<>();
        int length = text.length();
        // Ensure chunkSize is valid
        if (chunkSize <= 0) {
            throw new IllegalArgumentException("Chunk size must be positive");
        }
        // Split the text into chunks
        for (int i = 0; i < length; i += chunkSize) {
            chunks.add(text.substring(i, Math.min(length, i + chunkSize)));
        }
        return chunks;
    }
}
