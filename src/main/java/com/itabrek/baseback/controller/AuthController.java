package com.itabrek.baseback.controller;

import com.itabrek.baseback.authentication.AuthenticationService;
import com.itabrek.baseback.authentication.JwtAuthenticationResponse;
import com.itabrek.baseback.dto.RefreshRequest;
import com.itabrek.baseback.dto.SignInRequest;
import com.itabrek.baseback.dto.SignUpRequest;
import com.itabrek.baseback.exception.UserAlreadyExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@Valid @RequestBody SignUpRequest request) throws UserAlreadyExistsException {
        logger.info("AuthController register processing");
        return authenticationService.signUp(request);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse signIn(@Valid @RequestBody SignInRequest request) {
        logger.info("AuthController signIn processing");
        return authenticationService.signIn(request);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshRequest refreshRequest) {
        logger.info("AuthController refresh processing");

        try {
            if (!StringUtils.hasLength(refreshRequest.getRefresh())) {
                logger.warn("AuthController refresh processing bad request");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            return authenticationService.refresh(refreshRequest.getRefresh());
        } catch (Exception e) {
            logger.warn("AuthController refresh processing INTERNAL_SERVER_ERROR: " + e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}