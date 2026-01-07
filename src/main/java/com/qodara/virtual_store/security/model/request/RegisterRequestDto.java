package com.qodara.virtual_store.security.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto {
    @NotEmpty(message = "The email is required")
    @Email(message = "email format is invalid")
    private String email;

    @NotEmpty(message = "The email is required")
    @Email(message = "email format is invalid")
    private String name;

    @NotEmpty(message = "The email is required")
    @Email(message = "email format is invalid")
    private String lastname;

    @NotEmpty(message = "The password is required")
    @Size(min = 3, message = "the password must be at least 3 characters long")
    private String password;

    @NotNull(message = "Role is required")
    private Integer role;
}
