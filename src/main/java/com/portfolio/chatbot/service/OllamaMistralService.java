package com.portfolio.chatbot.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OllamaMistralService {
    private final WebClient webClient;
    public OllamaMistralService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:11434/api")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
    public Mono<String> generateResponse(String prompt) {
        return webClient.post()
                .uri("/generate")
                .bodyValue(Map.of(
                    "model", "mistral",
                    "prompt", prompt,
                    "stream", false
                ))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(response -> response.get("response").asText());
    }
}
