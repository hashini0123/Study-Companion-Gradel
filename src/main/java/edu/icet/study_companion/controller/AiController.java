package edu.icet.study_companion.controller;

import edu.icet.study_companion.service.OllamaService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final OllamaService ollamaService;

    public AiController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @PostMapping("/summarize")
    public Map<String, String> summarize(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        String summary = ollamaService.ask(
                "Summarize the following study material in clear bullet points:\n\n" + text);
        return Map.of("summary", summary);
    }
}