package com.fullStack.expenseTracker.controllers;

import com.fullStack.expenseTracker.services.AuthService;
import com.fullStack.expenseTracker.services.UserService;
import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import com.fullStack.expenseTracker.dto.requests.ResetPasswordRequestDto;
import com.fullStack.expenseTracker.exceptions.UserNotFoundException;
import com.fullStack.expenseTracker.exceptions.UserServiceLogicException;
import com.fullStack.expenseTracker.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/mywallet/user")
public class UserController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/settings/changePassword")
    public ResponseEntity<ApiResponseDto<?>> changePassword(@RequestBody @Valid ResetPasswordRequestDto resetPasswordRequestDto,
                                                             @AuthenticationPrincipal UserDetailsImpl user)
            throws UserNotFoundException, UserServiceLogicException {
        resetPasswordRequestDto.setEmail(user.getEmail());
        return authService.resetPassword(resetPasswordRequestDto);
    }

    @PostMapping("/settings/profileImg")
    public ResponseEntity<ApiResponseDto<?>> uploadProfileImg(@RequestParam(value = "email", required = false) String email,
                                                              @RequestParam("file") @Valid MultipartFile file,
                                                              @AuthenticationPrincipal UserDetailsImpl user)
            throws UserNotFoundException, UserServiceLogicException {
        return userService.uploadProfileImg(user.getEmail(), file);
    }

    @GetMapping("/settings/profileImg")
    public ResponseEntity<ApiResponseDto<?>> getProfileImg(@RequestParam(value = "email", required = false) String email,
                                                            @AuthenticationPrincipal UserDetailsImpl user)
            throws UserNotFoundException, UserServiceLogicException, IOException {
        return userService.getProfileImg(user.getEmail());
    }

    @DeleteMapping("/settings/profileImg")
    public ResponseEntity<ApiResponseDto<?>> deleteProfileImg(@RequestParam(value = "email", required = false) String email,
                                                               @AuthenticationPrincipal UserDetailsImpl user)
            throws UserNotFoundException, UserServiceLogicException, IOException {
        return userService.deleteProfileImg(user.getEmail());
    }
}
