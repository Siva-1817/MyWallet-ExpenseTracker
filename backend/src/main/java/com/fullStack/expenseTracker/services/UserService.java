package com.fullStack.expenseTracker.services;

import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import com.fullStack.expenseTracker.exceptions.UserNotFoundException;
import com.fullStack.expenseTracker.exceptions.UserServiceLogicException;
import com.fullStack.expenseTracker.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface UserService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String Email);

    User findByEmail(String email) throws UserNotFoundException;

    ResponseEntity<ApiResponseDto<?>> uploadProfileImg(String email, MultipartFile file) throws UserServiceLogicException, UserNotFoundException;

    ResponseEntity<ApiResponseDto<?>> getProfileImg(String email) throws UserNotFoundException, IOException, UserServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> deleteProfileImg(String email) throws UserServiceLogicException, UserNotFoundException;
}
