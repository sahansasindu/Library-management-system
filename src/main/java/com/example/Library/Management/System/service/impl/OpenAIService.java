package com.example.Library.Management.System.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    private final String API_KEY = "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";
    private final String URL = "https://models.inference.ai.azure.com/chat/completions";

    public String askLLM(String question) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o-mini");

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "user", "content", question));

        body.put("messages", messages);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(URL, request, Map.class);

        List<Map<String, Object>> choices =
                (List<Map<String, Object>>) response.getBody().get("choices");

        Map<String, Object> message =
                (Map<String, Object>) choices.get(0).get("message");

        return message.get("content").toString();
    }
}
