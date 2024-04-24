package com.itabrek.baseback.controller;

import com.itabrek.baseback.dto.UserDataRequest;
import com.itabrek.baseback.dto.UserDataResponse;
import com.itabrek.baseback.service.UserDataService;
import com.itabrek.baseback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDataService userDataService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/{username}")
    protected ResponseEntity<UserDataResponse> getUserByUsername(@PathVariable String username) {
        return userDataService.getUserData(username);
    }

    @PutMapping("/{username}")
    protected ResponseEntity<UserDataResponse> updateUserData(@RequestBody UserDataRequest userDataRequest) {
        logger.info("PROCESS UPDATE USER DATA");
        return userDataService.updateUserData(userDataRequest);
    }
}
