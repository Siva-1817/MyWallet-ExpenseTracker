package com.fullStack.expenseTracker.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min=3, max = 20)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    private boolean enabled;

    // Retained for databases created by the previous reset-code schema.
    @Builder.Default
    private boolean passwordResetAuthorized = false;

    private String profileImgUrl;

    public User(String username, String email, String password, boolean enabled) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }

}
