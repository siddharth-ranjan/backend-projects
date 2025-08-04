package com.hackathon.emojitranslatorapi.service;

import com.hackathon.emojitranslatorapi.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiService {

    @Value("${GEMINI_API_KEY}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

    public String callGemini(String userInput) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-goog-api-key", apiKey);

        String prompt = """
            Only return the transformed response. No labels, no comments.
            let's get coffee before our meeting
            let's get ☕ before our 🤝

            %s
            """.formatted(userInput);

        GeminiRequest.Body body = new GeminiRequest.Body(new GeminiRequest.Part[] {
                new GeminiRequest.Part(prompt)
        });

        GeminiRequest request = new GeminiRequest(new GeminiRequest.Body[] { body });

        HttpEntity<GeminiRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<GeminiResponse> response = restTemplate.exchange(
                GEMINI_URL, HttpMethod.POST, entity, GeminiResponse.class);

        return response.getBody()
                .getCandidates()[0]
                .getContent()
                .getParts()[0]
                .getText()
                .trim() + "\n";
    }
}
