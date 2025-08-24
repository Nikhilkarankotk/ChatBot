package com.portfolio.chatbot.service.RAG;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@Service
public class EmbeddingService {
    private final WebClient webClient;
    public EmbeddingService(@Value("${mistral.api.key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.mistral.ai/v1")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }
    public Mono<float[]> generateEmbedding(String text) {
        return webClient.post()
                .uri("/embeddings")
                .bodyValue(Map.of(
                    "model", "mistral-embed",
                    "input", text
                ))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(response -> {
                    JsonNode embeddingNode = response.get("data").get(0).get("embedding");
                    float[] embedding = new float[embeddingNode.size()];
                    for (int i = 0; i < embeddingNode.size(); i++) {
                        embedding[i] = embeddingNode.get(i).floatValue();
                    }
                    System.out.println("Generated Embedding: "+ Arrays.toString(embedding));
                    return embedding;
                });
    }
}
