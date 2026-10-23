package com.fullStack.expenseTracker.services.impls;

import com.fullStack.expenseTracker.dto.reponses.ApiResponseDto;
import com.fullStack.expenseTracker.dto.requests.LocalPasswordResetRequestDto;
import com.fullStack.expenseTracker.dto.requests.SignUpRequestDto;
import com.fullStack.expenseTracker.enums.ApiResponseStatus;
import com.fullStack.expenseTracker.models.User;
import com.fullStack.expenseTracker.repository.UserRepository;
import com.fullStack.expenseTracker.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(userService, userRepository, passwordEncoder);
    }

    @Test
    void registrationPersistsEnabledAccountWithEncodedPassword() throws Exception {
        when(userService.existsByUsername("wallet-user")).thenReturn(false);
        when(userService.existsByEmail("wallet@example.com")).thenReturn(false);
        when(passwordEncoder.encode("wallet-password")).thenReturn("bcrypt-hash");

        ResponseEntity<ApiResponseDto<?>> response = authService.save(
                new SignUpRequestDto("wallet-user", "wallet@example.com", "wallet-password")
        );

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertTrue(savedUser.isEnabled());
        assertEquals("wallet@example.com", savedUser.getEmail());
        assertEquals("bcrypt-hash", savedUser.getPassword());
        assertNotEquals("wallet-password", savedUser.getPassword());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ApiResponseStatus.SUCCESS, response.getBody().getStatus());
        assertEquals("Registration successful. You can now log in.", response.getBody().getResponse());
    }

    @Test
    void localDemoPasswordResetPersistsOnlyBcryptHash() throws Exception {
        User user = new User();
        user.setEmail("wallet@example.com");
        user.setPassword("old-hash");
        when(userService.findByEmail("wallet@example.com")).thenReturn(user);
        when(passwordEncoder.encode("new-password")).thenReturn("new-bcrypt-hash");
        LocalPasswordResetRequestDto request = new LocalPasswordResetRequestDto();
        request.setEmail("wallet@example.com");
        request.setNewPassword("new-password");
        request.setConfirmPassword("new-password");

        ResponseEntity<ApiResponseDto<?>> response = authService.resetPasswordForLocalDemo(request);

        verify(userRepository).save(user);
        assertEquals("new-bcrypt-hash", user.getPassword());
        assertNotEquals("new-password", user.getPassword());
        assertFalse(response.getBody().getResponse().toString().contains("new-bcrypt-hash"));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ApiResponseStatus.SUCCESS, response.getBody().getStatus());
    }

    @Test
    void localDemoPasswordResetRejectsMismatchedConfirmation() throws Exception {
        LocalPasswordResetRequestDto request = new LocalPasswordResetRequestDto();
        request.setEmail("wallet@example.com");
        request.setNewPassword("new-password");
        request.setConfirmPassword("different-password");

        ResponseEntity<ApiResponseDto<?>> response = authService.resetPasswordForLocalDemo(request);

        verify(userRepository, never()).save(org.mockito.ArgumentMatchers.any());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
