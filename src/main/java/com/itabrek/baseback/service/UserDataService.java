package com.itabrek.baseback.service;

import com.itabrek.baseback.dto.UserDataRequest;
import com.itabrek.baseback.dto.UserDataResponse;
import com.itabrek.baseback.dto.UserResponse;
import com.itabrek.baseback.entity.User;
import com.itabrek.baseback.entity.UserData;
import com.itabrek.baseback.exception.UserNotFoundException;
import com.itabrek.baseback.repository.UserDataRepository;
import com.itabrek.baseback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDataService {
    private final UserDataRepository userDataRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    private static final Logger logger = LoggerFactory.getLogger(UserDataService.class);

    public UserDataResponse getUserData(String username) throws UserNotFoundException {
        UserData userData = userDataRepository.findByUserUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        return modelMapper.map(userData, UserDataResponse.class);
    }

    public UserData getUserDataByUsername(String username) throws UserNotFoundException {
        Optional<UserData> userData = userDataRepository.findByUserUsername(username);
        return userData.orElseThrow(() -> new UserNotFoundException("USER NOT FOUND"));
    }

    public ResponseEntity<UserDataResponse> updateUserData(UserDataRequest updatedUserData) {
        User currentUser = userService.getCurrentUser();

        UserData currentUserData = userDataRepository.findUserDataByUser(currentUser).orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND"));

        UserData updatedResult = userDataRepository.save(UserData.builder()
                        .id(currentUserData.getId())
                        .user(currentUserData.getUser())
                        .fullName(updatedUserData.getFullName())
                        .phone(updatedUserData.getPhone())
                        .dateOfBirth(updatedUserData.getDateOfBirth())
                .build());

        UserDataResponse response = new UserDataResponse(updatedResult);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
