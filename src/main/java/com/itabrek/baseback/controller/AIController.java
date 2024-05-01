package com.itabrek.baseback.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIController {

    private static final Logger logger = LoggerFactory.getLogger(AIController.class);
    private final OllamaApi ollamaApi = new OllamaApi();

    private final OllamaChatClient chatClient;

    @GetMapping("/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        logger.info("START AI GENERATE EXECUTE");
        Prompt prompt = new Prompt(new UserMessage(message));
        return Map.of("generation", chatClient.call(prompt));
    }

    @GetMapping("/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke and tell me about programming") String message) {
        logger.info("START AI STREAM GENERATE EXECUTE");
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatClient.stream(prompt);
    }
}
