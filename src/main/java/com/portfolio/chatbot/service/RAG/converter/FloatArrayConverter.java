package com.portfolio.chatbot.service.RAG.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.stream.Collector;

@Converter
public class FloatArrayConverter implements AttributeConverter<float[], String> {
    @Override
    public String convertToDatabaseColumn(float[] attribute) {
        if (attribute == null || attribute.length == 0) return null;
        return Arrays.toString(attribute);
    }
    @Override
    public float[] convertToEntityAttribute(String dbData) {
//    return Arrays.stream(dbData.substring(1, dbData.length() - 1).split(","))
//                .map(String::trim)
//                .mapToFloat(Float::parseFloat)
//                .toArray();
//        }
        if(dbData == null || dbData.isEmpty()) return new float[0];
        return Arrays.stream(dbData.substring(1, dbData.length() - 1).split(",")).map(String::trim)
                .map(Float::parseFloat)
                .collect(
                        Collector.of(
                                () -> new FloatArrayCollector(),              // supplier
                                FloatArrayCollector::add,                    // accumulator
                                FloatArrayCollector::combine,                // combiner
                                FloatArrayCollector::toFloatArray            // finisher
                        )
                );
    }
}
