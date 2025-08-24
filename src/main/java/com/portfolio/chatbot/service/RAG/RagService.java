package com.portfolio.chatbot.service.RAG;

import com.portfolio.chatbot.model.DocumentChunk;
import com.portfolio.chatbot.repository.DocumentChunkRepository;
import com.portfolio.chatbot.service.MistralApiService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RagService {
    private final DocumentParser documentParser;
    private final TextChunker textChunker;
    private final EmbeddingService embeddingService;
    private final DocumentChunkRepository chunkRepo;
    private final MistralApiService mistralApiService;

    @Transactional
    public void ingestDocument(MultipartFile file) throws IOException, TikaException {
        String text = documentParser.parseDocument(file);
        List<String> chunks = textChunker.chunkText(text, 500);
        for (String chunk : chunks) {
//            float[] embedding = embeddingService.generateEmbedding(chunk).block();
            DocumentChunk docChunk = new DocumentChunk();
            docChunk.setText(chunk);
//            docChunk.setEmbedding(embedding);
            chunkRepo.save(docChunk);
        }
    }
    public String query(String userQuery) {
        float[] queryEmbedding = embeddingService.generateEmbedding(userQuery).block();
        List<DocumentChunk> relevantChunks = chunkRepo.findSimilarChunks(Arrays.toString(queryEmbedding));
        
        String context = relevantChunks.stream()
                .map(DocumentChunk::getText)
                .collect(Collectors.joining("\n"));
        String augmentedPrompt = """
            You are a portfolio assistant. Use the following context to answer the user's question.
            If you dont know, say you dont know.
            Context: %s
            Question: %s
            """.formatted(context, userQuery);
        return mistralApiService.generateResponse(augmentedPrompt);
    }
}
