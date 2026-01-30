package com.notificationservice.services;

import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.notificationservice.dtos.ApiResponseDto;
import com.notificationservice.dtos.MailRequestDto;

import java.io.UnsupportedEncodingException;

@Service
public interface NotificationService {
    ResponseEntity<ApiResponseDto<?>> sendEmail(MailRequestDto requestDto);
}
