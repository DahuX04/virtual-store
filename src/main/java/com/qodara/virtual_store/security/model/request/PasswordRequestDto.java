package com.qodara.virtual_store.security.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordRequestDto {
    @NotEmpty(message = "The current password is required")
    private String currentPassword;


    @NotEmpty(message = "The password is required")
    @Size(min = 3, message = "the password must be at least 3 characters long")
    private String password;
}
