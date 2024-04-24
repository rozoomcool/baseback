package com.itabrek.baseback.service;

import com.itabrek.baseback.dto.CommentRequest;
import com.itabrek.baseback.dto.StoryRequest;
import com.itabrek.baseback.entity.Comment;
import com.itabrek.baseback.entity.Story;
import com.itabrek.baseback.repository.CommentRepository;
import com.itabrek.baseback.repository.StoryRepository;
import com.itabrek.baseback.utils.FileUploadUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoryService {
    @Value("${upload.path}")
    private String uploadPath;
    private final StoryRepository storyRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private static final Logger logger = LoggerFactory.getLogger(StoryService.class);

    public Story getShortById(Long id) {
        return storyRepository.findById(id).get();
    }

    public ResponseEntity<Story> saveStory(StoryRequest story) throws IOException {
        logger.debug("title");
        String filename;
        try {
             filename = FileUploadUtil.saveFile(story.getVideo());
        } catch (Exception e) {
            return new ResponseEntity("Unable save file: " + e, HttpStatus.BAD_REQUEST);
        }

        Story saved = Story.builder().title(story.getTitle()).videoTitle(filename).user(userService.getCurrentUser()).build();
        return new ResponseEntity(storyRepository.save(saved), HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity addComment(CommentRequest request) {
        Comment comment = Comment.builder()
                .body(request.getCommentBody())
                .user(userService.getCurrentUser())
                .build();
        Story currentStory = storyRepository.findById(request.getStoryId()).get();
        List<Comment> comments = currentStory.getComments();
        comments.add(comment);
        commentRepository.save(comment);
        currentStory.setComments(comments);

        return new ResponseEntity(storyRepository.save(currentStory), HttpStatus.OK);
    }
}
