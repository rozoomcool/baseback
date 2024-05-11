package com.itabrek.baseback.controller;

import com.itabrek.baseback.dto.UserDataRequest;
import com.itabrek.baseback.dto.UserDataResponse;
import com.itabrek.baseback.exception.UserNotFoundException;
import com.itabrek.baseback.service.UserDataService;
import com.itabrek.baseback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDataService userDataService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{username}")
    protected ResponseEntity<UserDataResponse> getUserDataByUsername(@PathVariable String username) {
        logger.info("EXECUTE GET USER DATA BY USERNAME");
        try {
            return ResponseEntity.ok(userDataService.getUserData(username));
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    protected ResponseEntity<UserDataResponse> getUser(Principal principal) {
        logger.info("EXECUTE GET USER DATA BY USERNAME");
        try {
            return ResponseEntity.ok(userDataService.getUserData(principal.getName()));
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    protected ResponseEntity<UserDataResponse> updateUserData(@RequestBody UserDataRequest userDataRequest) {
        logger.info("EXECUTE UPDATE USER DATA");
        return userDataService.updateUserData(userDataRequest);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception ex) {
        logger.error(ex.getMessage());
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
    }
}
