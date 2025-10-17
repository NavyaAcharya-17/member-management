package com.surest.member_management.service;

import com.surest.member_management.dto.AuthRequest;
import com.surest.member_management.dto.AuthResponse;

public interface AuthService {

    AuthResponse generateToken(AuthRequest authRequest) throws Exception;
}
