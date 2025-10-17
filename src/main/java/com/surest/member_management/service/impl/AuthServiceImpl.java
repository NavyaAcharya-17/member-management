package com.surest.member_management.service.impl;

import com.surest.member_management.dto.AuthRequest;
import com.surest.member_management.dto.AuthResponse;
import com.surest.member_management.service.AuthService;
import com.surest.member_management.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    //Authenticate user and generate JWT token
    @Override
    public AuthResponse generateToken(AuthRequest authRequest) throws Exception {
        //Authenticate user credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        //Generate JWT token for authenticated user
        String token = jwtUtil.generateToken(authRequest.getUsername());

        //Return token response
        return AuthResponse.builder().token(token).build();
    }
}
