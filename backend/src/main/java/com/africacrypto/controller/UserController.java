package com.africacrypto.controller;

import com.africacrypto.dto.*;
import com.africacrypto.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest request) {
        System.out.println("🚀 Register endpoint hit with: " + request);

        UserDTO response = userService.register(request);

        System.out.println("✅ Created user: " + response);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        System.out.println("🔐 Login endpoint hit for: " + request.getEmail());
        String token = userService.login(request);
        System.out.println("🎫 Token issued: " + token);
        return ResponseEntity.ok(token);
    }


}
