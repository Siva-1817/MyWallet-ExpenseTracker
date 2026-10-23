package com.fullStack.expenseTracker.controllers;

import com.fullStack.expenseTracker.services.AuthService;
import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import com.fullStack.expenseTracker.dto.requests.LocalPasswordResetRequestDto;
import com.fullStack.expenseTracker.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/mywallet/auth/forgotPassword")
public class ForgotPasswordController {

    private final AuthService authService;

    public ForgotPasswordController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ApiResponseDto<?>> resetPassword(
            @RequestBody @Valid LocalPasswordResetRequestDto resetPasswordDto) throws UserNotFoundException {
        return authService.resetPasswordForLocalDemo(resetPasswordDto);
    }
}
