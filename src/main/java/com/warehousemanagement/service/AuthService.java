package com.warehousemanagement.service;

import com.warehousemanagement.dto.request.LoginRequest;
import com.warehousemanagement.dto.request.RegisterRequest;
import com.warehousemanagement.dto.response.AuthResponse;
import com.warehousemanagement.entity.UserEntity;
import com.warehousemanagement.exception.BadRequestException;
import com.warehousemanagement.repository.UserRepository;
import com.warehousemanagement.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  @Transactional
  public AuthResponse register(RegisterRequest request) {

    if (userRepository.existsByUsername(request.getUsername())) {
      throw new BadRequestException("Username already exists");
    }

    UserEntity user = UserEntity.builder()
        .username(request.getUsername().trim())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();

    UserEntity savedUser = userRepository.save(user);

    return new AuthResponse(jwtService.generateToken(savedUser));
  }

  @Transactional
  public AuthResponse login(LoginRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );

    UserEntity user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new BadRequestException("Invalid username or password"));

    return new AuthResponse(jwtService.generateToken(user));
  }


}
