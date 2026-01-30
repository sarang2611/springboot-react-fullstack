package com.userservice.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.userservice.dtos.ApiResponseDto;
import com.userservice.dtos.UserDto;
import com.userservice.exceptions.ServiceLogicException;
import com.userservice.exceptions.UserNotFoundException;
import com.userservice.modals.User;
import com.userservice.repositories.UserRepository;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponseDto<?>> existsUserById(String userId) throws ServiceLogicException {
        try {
            boolean exists = userRepository.existsById(userId);
            return ResponseEntity.ok(ApiResponseDto.builder()
                    .isSuccess(true)
                    .response(exists)
                    .message(exists ? "User exists." : "User does not exist.")
                    .build());
        } catch (Exception e) {
            log.error("Error checking user existence: {}", e.getMessage());
            throw new ServiceLogicException("Something went wrong. Try again later!");
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> getUserById(String id) throws ServiceLogicException, UserNotFoundException {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));

            return ResponseEntity.ok(ApiResponseDto.builder()
                    .isSuccess(true)
                    .response(userToUserDto(user))
                    .message("User retrieved successfully.")
                    .build());
        } catch (UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error fetching user: {}", e.getMessage());
            throw new ServiceLogicException("Something went wrong. Try again later!");
        }
    }

    private UserDto userToUserDto(User user) {
        return UserDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
