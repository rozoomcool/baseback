package com.itabrek.baseback.authentication;

import com.itabrek.baseback.dto.SignInRequest;
import com.itabrek.baseback.dto.SignUpRequest;
import com.itabrek.baseback.entity.Role;
import com.itabrek.baseback.entity.User;
import com.itabrek.baseback.exception.UserAlreadyExistsException;
import com.itabrek.baseback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<JwtAuthenticationResponse> signUp(SignUpRequest request) throws UserAlreadyExistsException {

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .role(Role.USER)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        try{
            userService.create(user);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        var access = accessTokenService.generateToken(user);
        var refresh = refreshTokenService.generateToken(user);
        return new ResponseEntity<>(new JwtAuthenticationResponse(refresh, access), HttpStatus.CREATED);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var access = accessTokenService.generateToken(user);
        var refresh = refreshTokenService.generateToken(user);
        return new JwtAuthenticationResponse(refresh, access);
    }

    public ResponseEntity<JwtAuthenticationResponse> refresh(String refresh) {
        String username = refreshTokenService.extractUsername(refresh);
        var user = userService
                .userDetailsService()
                .loadUserByUsername(username);

        if (!refreshTokenService.isTokenValid(refresh, user)){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        var access = accessTokenService.generateToken(user);
        var newRefresh = refreshTokenService.generateToken(user);
        return new ResponseEntity<>(new JwtAuthenticationResponse(newRefresh, access), HttpStatus.OK);
    }
}
