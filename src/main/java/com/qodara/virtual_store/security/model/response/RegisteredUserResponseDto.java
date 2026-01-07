package com.qodara.virtual_store.security.model.response;

import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredUserResponseDto {
    private int id;
    private String email;
    private String name;
    private String lastname;
    private Role role;
}
