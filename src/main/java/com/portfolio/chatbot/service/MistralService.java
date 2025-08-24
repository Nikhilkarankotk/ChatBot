package com.portfolio.chatbot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

@Service
public class MistralService {

    @Value("${mistral.api.key}")
    private String mistralApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String uploadDocumentAndSendMessage(MultipartFile file, String message) throws IOException {
        String mistralUrl = "https://api.mistral.ai/v1/files"; // Upload endpoint

        // 1️⃣ Create request for file upload
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
        body.add("purpose", "ocr");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(mistralApiKey);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> uploadResponse = restTemplate.exchange(mistralUrl, HttpMethod.POST, requestEntity, String.class);

        // Extract file_id from Mistral's response (JSON parsing needed)
        String fileId = extractFileId(uploadResponse.getBody());

        // 2️⃣ Send message + file_id to Mistral's processing endpoint
        String processUrl = "https://api.mistral.ai/v1/ocr/process";

        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        jsonHeaders.setBearerAuth(mistralApiKey);

        String payload = """
        {
          "file_id": "%s",
          "message": "%s"
        }
        """.formatted(fileId, message);

        HttpEntity<String> processRequest = new HttpEntity<>(payload, jsonHeaders);
        ResponseEntity<String> processResponse = restTemplate.postForEntity(processUrl, processRequest, String.class);

        return processResponse.getBody();
    }

    private String extractFileId(String responseJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseJson);
            return root.path("id").asText(); // returns "" if not found
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse file_id from Mistral response: " + e.getMessage());
        }
    }

}
