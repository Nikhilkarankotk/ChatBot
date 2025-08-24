package com.portfolio.chatbot.model;

import com.portfolio.chatbot.service.RAG.converter.FloatArrayConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class DocumentChunk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String text;
//    @Convert(converter = FloatArrayConverter.class)
//    @Column(columnDefinition = "vector(1536)")
//    private float[] embedding; // Store embeddings as float[]
//}
}
