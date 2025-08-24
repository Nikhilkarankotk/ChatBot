package com.portfolio.chatbot.service.RAG.converter;

import java.util.ArrayList;
import java.util.List;

public class FloatArrayCollector {
    private final List<Float> list = new ArrayList<>();

    public void add(Float f) {
        list.add(f);
    }

    public FloatArrayCollector combine(FloatArrayCollector other) {
        list.addAll(other.list);
        return this;
    }

    public float[] toFloatArray() {
        float[] result = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }
}
