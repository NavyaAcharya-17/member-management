package com.surest.member_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private UUID id;
    private String username;
    private Set<String> roles;

    public static UserResponse fromEntity(com.surest.member_management.entity.User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles().stream().map(com.surest.member_management.entity.Role::getName).collect(java.util.stream.Collectors.toSet()))
                .build();
    }
}
