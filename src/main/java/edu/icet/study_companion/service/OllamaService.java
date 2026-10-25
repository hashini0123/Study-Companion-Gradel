package edu.icet.study_companion.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class OllamaService {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String model;

    public OllamaService(@Value("${ollama.base-url}") String baseUrl,
                         @Value("${ollama.model}") String model) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5_000);
        factory.setReadTimeout(120_000);
        this.restTemplate = new RestTemplate(factory);
        this.baseUrl = baseUrl;
        this.model = model;
    }

    public String ask(String prompt) {
        Map<String, Object> body = Map.of(
                "model", model,
                "stream", false,
                "messages", List.of(Map.of("role", "user", "content", prompt))
        );

        JsonNode response = restTemplate.postForObject(baseUrl + "/api/chat", body, JsonNode.class);

        if (response == null || response.path("message").path("content").isMissingNode()) {
            throw new IllegalStateException("Ollama returned an unexpected response");
        }
        return response.path("message").path("content").asText();
    }
}