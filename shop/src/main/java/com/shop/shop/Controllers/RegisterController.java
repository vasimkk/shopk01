package com.shop.shop.Controllers;

// import javax.management.relation.Role; // Remove this import if not needed

import com.shop.shop.Entity.Role; // Add this import if you have a Role enum/class in your Entity package

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shop.Entity.AuthResponse;
import com.shop.shop.Entity.LoginRequest;
import com.shop.shop.Entity.Register;
import com.shop.shop.Entity.RegisterRequest;
import com.shop.shop.Services.RegisterService;

@RestController
@RequestMapping("/api/auth/shop")
public class RegisterController {

   @Autowired
    private RegisterService registerService;

    @PostMapping("/register/user")
    public ResponseEntity<Register> registerUser(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(registerService.registerUser(request, Role.USER));
    }

    @PostMapping("/register/seller")
    public ResponseEntity<Register> registerSeller(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(registerService.registerUser(request, Role.SELLER));
    }

    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Register> registerAdmin(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(registerService.registerUser(request, Role.ADMIN));
    }


    // ✅ Login endpoint
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(registerService.login(request));
    }
}
