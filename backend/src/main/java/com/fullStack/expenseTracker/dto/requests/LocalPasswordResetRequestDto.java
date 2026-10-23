package com.fullStack.expenseTracker.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocalPasswordResetRequestDto {
    @NotBlank(message = "Email is required!")
    @Email(message = "Email is not in valid format!")
    private String email;

    @NotBlank(message = "New password is required!")
    @Size(min = 8, max = 20, message = "New password must have between 8 and 20 characters!")
    private String newPassword;

    @NotBlank(message = "Password confirmation is required!")
    private String confirmPassword;
}
