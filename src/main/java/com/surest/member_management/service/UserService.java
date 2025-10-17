package com.surest.member_management.service;

import com.surest.member_management.dto.UserRequest;
import com.surest.member_management.dto.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);
}
