package com.itabrek.baseback.service;

import com.itabrek.baseback.dto.UserResponse;
import com.itabrek.baseback.entity.Role;
import com.itabrek.baseback.entity.User;
import com.itabrek.baseback.entity.UserData;
import com.itabrek.baseback.exception.UserAlreadyExistsException;
import com.itabrek.baseback.exception.UserNotFoundException;
import com.itabrek.baseback.repository.UserDataRepository;
import com.itabrek.baseback.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserDataRepository userDataRepository;

    public ResponseEntity<UserResponse> findUserById(Long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);

        return user.map(value -> new ResponseEntity<>(
                        UserResponse.builder()
                                .username(value.getUsername())
                                .email(value.getEmail()).build(),    HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<UserResponse> findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(value -> new ResponseEntity<>(UserResponse.builder()
                        .username(value.getUsername())
                        .email(value.getEmail()).build(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<UserResponse> updateUsernameById(Long id, String username) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            if (userRepository.existsByUsername(username)) {
                User updatedUser = userData.get();
                updatedUser.setUsername(username);
                User updatedResult = userRepository.save(updatedUser);
                return new ResponseEntity<>(UserResponse.builder()
                        .username(updatedResult.getUsername())
                        .email(updatedResult.getEmail()).build(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<UserResponse> updateEmailById(Long id, String email) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            if (userRepository.existsByEmail(email)) {
                User updatedUser = userData.get();
                updatedUser.setEmail(email);
                User updatedResult = userRepository.save(updatedUser);
                return new ResponseEntity<>(UserResponse.builder()
                        .username(updatedResult.getUsername())
                        .email(updatedResult.getEmail()).build(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<HttpStatus> deleteStudent(Long id) {
        try {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void create(User request) throws UserAlreadyExistsException {
        if (
                userRepository.existsByEmail(request.getEmail()) || userRepository.existsByUsername(request.getUsername())
        ) {
            throw new UserAlreadyExistsException("User alredy exists!");
        }

        User created = userRepository.save(User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.USER)
                .build());

        UserData userData = new UserData();
        userData.setUser(created);

        userDataRepository.save(userData);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }
}
