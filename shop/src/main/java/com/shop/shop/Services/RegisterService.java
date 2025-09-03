package com.shop.shop.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shop.shop.Entity.AuthResponse;
import com.shop.shop.Entity.LoginRequest;
import com.shop.shop.Entity.Register;
import com.shop.shop.Entity.RegisterRequest;
import com.shop.shop.Entity.Role;
import com.shop.shop.Repository.RegisterRepository;
import com.shop.shop.Security.JwtUtil;

@Service
public class RegisterService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Registration
    public Register registerUser(RegisterRequest request, Role role) {
        // Check if mobile number already exists
        if (registerRepository.findByMobileNumber(request.getMobileNumber()).isPresent()) {
            throw new RuntimeException("Mobile number already registered");
        }

        // Map DTO -> Entity
        Register user = new Register();
        user.setName(request.getName());
        user.setMobileNumber(request.getMobileNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // ✅ encode password
        user.setRole(role);

        return registerRepository.save(user);
    }

    // ✅ Login
    public AuthResponse login(LoginRequest request) {
        Register user = registerRepository.findByMobileNumber(request.getMobileNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtUtil.generateAccessToken(user.getMobileNumber(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getMobileNumber());

        return new AuthResponse(accessToken, refreshToken);
    }

    // ✅ Refresh
    public AuthResponse refresh(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String username = jwtUtil.getUsernameFromToken(refreshToken);
        Register user = registerRepository.findByMobileNumber(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtUtil.generateAccessToken(user.getMobileNumber(), user.getRole().name());
        String newRefreshToken = jwtUtil.generateRefreshToken(user.getMobileNumber());

        return new AuthResponse(newAccessToken, newRefreshToken);
    }
}
