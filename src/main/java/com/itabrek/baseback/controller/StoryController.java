package com.itabrek.baseback.controller;

import com.itabrek.baseback.dto.CommentRequest;
import com.itabrek.baseback.dto.StoryRequest;
import com.itabrek.baseback.entity.Story;
import com.itabrek.baseback.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/shorts")
@RequiredArgsConstructor
public class StoryController {

    private final StoryService storyService;
    private static final Logger logger = LoggerFactory.getLogger(StoryController.class);

    @GetMapping("/{id}")
    protected ResponseEntity<Story> getStory(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(storyService.getShortById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    protected ResponseEntity postStory(@ModelAttribute StoryRequest storyRequest) throws IOException {
        try {
            logger.error("successfully post story");
            return storyService.saveStory(storyRequest);
        } catch(Exception e) {
            logger.error("Error in post story: " + e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/comment")
    protected ResponseEntity postComment(@RequestBody CommentRequest request) {
        return storyService.addComment(request);
    }
}
