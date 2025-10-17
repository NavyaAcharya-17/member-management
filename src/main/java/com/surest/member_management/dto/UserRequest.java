package com.surest.member_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank(message = "Username is required")
    @Size(max = 50)
    private String username;;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotEmpty(message = "At least one role is required")
    private Set<String> roles;

    public com.surest.member_management.entity.User toEntity(String encodedPassword, Set<com.surest.member_management.entity.Role> roles) {
        return com.surest.member_management.entity.User.builder()
                .username(this.username)
                .passwordHash(encodedPassword)
                .roles(roles)
                .build();
    }
}
