package com.portfolio.chatbot.controller;

import com.portfolio.chatbot.service.MistralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/mistral")
public class MistralController {

    @Autowired
    private MistralService mistralService;

    @PostMapping(value = "/upload")
    public ResponseEntity<?> uploadFileAndMessage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("message") String message) {
        try {
            String response = mistralService.uploadDocumentAndSendMessage(file, message);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
