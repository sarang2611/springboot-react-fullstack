package com.authservice.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.authservice.dtos.ApiResponseDto;
import com.authservice.dtos.SignInRequestDto;
import com.authservice.dtos.SignUpRequestDto;
import com.authservice.exceptions.ServiceLogicException;
import com.authservice.exceptions.UserAlreadyExistsException;
import com.authservice.exceptions.UserNotFoundException;
import com.authservice.exceptions.UserVerificationFailedException;

import java.io.UnsupportedEncodingException;

@Service
public interface AuthService {
    ResponseEntity<ApiResponseDto<?>> registerUser(SignUpRequestDto signUpRequestDto) throws UnsupportedEncodingException, UserAlreadyExistsException, ServiceLogicException;
    ResponseEntity<ApiResponseDto<?>> resendVerificationCode(String email) throws UnsupportedEncodingException, UserNotFoundException, ServiceLogicException;
    ResponseEntity<ApiResponseDto<?>> verifyRegistrationVerification(String code) throws UserVerificationFailedException;
    ResponseEntity<ApiResponseDto<?>> authenticateUser(SignInRequestDto signInRequestDto);
    ResponseEntity<ApiResponseDto<?>> validateToken(String token);
}
