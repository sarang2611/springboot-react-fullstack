package com.userservice;

import com.userservice.controllers.UserController;
import com.userservice.dtos.ApiResponseDto;
import com.userservice.exceptions.ServiceLogicException;
import com.userservice.exceptions.UserNotFoundException;
import com.userservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testExistsUserById() throws ServiceLogicException {
        String userId = "123";
        ApiResponseDto<Boolean> responseDto = new ApiResponseDto(true, "User exists", true);
        ResponseEntity<ApiResponseDto<?>> responseEntity = ResponseEntity.ok(responseDto);


        when(userService.existsUserById(userId)).thenReturn(responseEntity);

        ResponseEntity<ApiResponseDto<?>> result = userController.existsUserById(userId);

        assertEquals(200, result.getStatusCodeValue());
        assertTrue((Boolean) result.getBody().getData());
        verify(userService, times(1)).existsUserById(userId);
    }

    @Test
    public void testGetUserById() throws ServiceLogicException, UserNotFoundException {
        String userId = "456";
        ApiResponseDto<String> responseDto2 = new ApiResponseDto<>(true, "User found", "John Doe");
        ResponseEntity<ApiResponseDto<?>> responseEntity = ResponseEntity.ok(responseDto2);

        when(userService.getUserById(userId)).thenReturn(responseEntity);

        ResponseEntity<ApiResponseDto<?>> result = userController.getUserById(userId);

        assertEquals(200, result.getStatusCodeValue());
        assertEquals("John Doe", result.getBody().getData());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    public void testExistsUserById_ServiceLogicException() throws ServiceLogicException {
        String userId = "123";

        when(userService.existsUserById(userId)).thenThrow(new ServiceLogicException("Internal error"));

        ServiceLogicException exception = assertThrows(ServiceLogicException.class, () -> {
            userController.existsUserById(userId);
        });

        assertEquals("Internal error", exception.getMessage());
        verify(userService, times(1)).existsUserById(userId);
    }

    @Test
    public void testGetUserById_UserNotFoundException() throws ServiceLogicException, UserNotFoundException {
        String userId = "999";

        when(userService.getUserById(userId)).thenThrow(new UserNotFoundException("User not found"));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userController.getUserById(userId);
        });

        assertEquals("User not found", exception.getMessage());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    public void testGetUserById_ServiceLogicException() throws ServiceLogicException, UserNotFoundException {
        String userId = "456";

        when(userService.getUserById(userId)).thenThrow(new ServiceLogicException("Service failure"));

        ServiceLogicException exception = assertThrows(ServiceLogicException.class, () -> {
            userController.getUserById(userId);
        });

        assertEquals("Service failure", exception.getMessage());
        verify(userService, times(1)).getUserById(userId);
    }
}
