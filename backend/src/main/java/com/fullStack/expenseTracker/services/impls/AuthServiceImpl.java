package com.fullStack.expenseTracker.services.impls;

import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import com.fullStack.expenseTracker.dto.requests.LocalPasswordResetRequestDto;
import com.fullStack.expenseTracker.dto.requests.ResetPasswordRequestDto;
import com.fullStack.expenseTracker.dto.requests.SignUpRequestDto;
import com.fullStack.expenseTracker.enums.ApiResponseStatus;
import com.fullStack.expenseTracker.exceptions.UserAlreadyExistsException;
import com.fullStack.expenseTracker.exceptions.UserNotFoundException;
import com.fullStack.expenseTracker.exceptions.UserServiceLogicException;
import com.fullStack.expenseTracker.models.User;
import com.fullStack.expenseTracker.repository.UserRepository;
import com.fullStack.expenseTracker.services.AuthService;
import com.fullStack.expenseTracker.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            UserService userService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> save(SignUpRequestDto request)
            throws UserAlreadyExistsException {
        if (userService.existsByUsername(request.getUserName())) {
            throw new UserAlreadyExistsException("Registration Failed: username is already taken!");
        }
        if (userService.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Registration Failed: email is already taken!");
        }

        User user = new User();
        user.setUsername(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(
                ApiResponseStatus.SUCCESS,
                HttpStatus.CREATED,
                "Registration successful. You can now log in."
        ));
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> resetPassword(ResetPasswordRequestDto request)
            throws UserNotFoundException, UserServiceLogicException {
        User user = userService.findByEmail(request.getEmail());
        String currentPassword = request.getCurrentPassword();

        if (currentPassword == null || currentPassword.isBlank()
                || !passwordEncoder.matches(currentPassword, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponseDto<>(
                    ApiResponseStatus.FAILED,
                    HttpStatus.FORBIDDEN,
                    "Reset password not successful: current password is incorrect!"
            ));
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(
                ApiResponseStatus.SUCCESS,
                HttpStatus.CREATED,
                "Reset successful: Password has been successfully reset!"
        ));
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> resetPasswordForLocalDemo(LocalPasswordResetRequestDto request)
            throws UserNotFoundException {
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(new ApiResponseDto<>(
                    ApiResponseStatus.FAILED,
                    HttpStatus.BAD_REQUEST,
                    "New password and confirmation do not match."
            ));
        }

        User user = userService.findByEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(new ApiResponseDto<>(
                ApiResponseStatus.SUCCESS,
                HttpStatus.OK,
                "Password reset successful. You can now log in."
        ));
    }
}
