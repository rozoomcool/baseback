package com.itabrek.baseback.controller;

import lombok.RequiredArgsConstructor;
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

    OllamaApi ollamaApi = new OllamaApi();

    OllamaChatClient chatClient = new OllamaChatClient(ollamaApi).withModel("llama3")
            .withDefaultOptions(OllamaOptions.create()
                    .withModel(OllamaOptions.DEFAULT_MODEL)
                    .withTemperature(0.9f));

    @GetMapping("/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatClient.call(new Prompt(
                message,
                OllamaOptions.create()
                        .withModel("llama3")
                        .withTemperature(0.4f)
        )));
    }

    @GetMapping("/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatClient.stream(new Prompt(
                message,
                OllamaOptions.create()
                        .withModel("llama3")
                        .withTemperature(0.4f)
        ));
    }
}
