package com.surest.member_management.service.impl;

import com.surest.member_management.dto.UserRequest;
import com.surest.member_management.dto.UserResponse;
import com.surest.member_management.entity.Role;
import com.surest.member_management.entity.User;
import com.surest.member_management.exception.ResourceAlreadyExistsException;
import com.surest.member_management.repository.RoleRepository;
import com.surest.member_management.repository.UserRepository;
import com.surest.member_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new ResourceAlreadyExistsException();
        }

        Set<Role> roles = roleRepository.findByNameIn(userRequest.getRoles());
        if (roles.isEmpty()) {
            throw new IllegalArgumentException("At least one valid role must be provided");
        }

        User user = userRequest.toEntity(passwordEncoder.encode(userRequest.getPassword()), roles);
        return UserResponse.fromEntity(userRepository.save(user));
    }

}
