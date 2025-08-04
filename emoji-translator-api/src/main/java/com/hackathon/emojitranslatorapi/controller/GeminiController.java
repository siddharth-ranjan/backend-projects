package com.hackathon.emojitranslatorapi.controller;

import com.hackathon.emojitranslatorapi.service.GeminiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    private final GeminiService service;

    public GeminiController(GeminiService service) {
        this.service = service;
    }

    @GetMapping("/transform")
    public String transform(@RequestParam String input) {
        return service.callGemini(input);
    }
}
