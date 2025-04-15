package com.africacrypto.service.impl;

import com.africacrypto.dto.LoginRequest;
import com.africacrypto.dto.RegisterRequest;
import com.africacrypto.dto.UserDTO;
import com.africacrypto.entity.User;
import com.africacrypto.repository.UserRepository;
import com.africacrypto.service.UserService;
import com.africacrypto.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserDTO register(RegisterRequest request) {
        System.out.println("📥 Received register request for: " + request.getEmail());

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .enabled(true)              // ✅ Ensures user is not disabled
                .kycVerified(false)         // ✅ Default KYC status
                .build();

        User saved = userRepository.save(user);

        System.out.println("💾 Saved user with ID: " + saved.getId());

        return UserDTO.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .email(saved.getEmail())
                .role(saved.getRole())
                .build();
    }

    @Override
    public String login(LoginRequest request) {
        System.out.println("🔐 Login request for: " + request.getEmail());

        // Authenticate credentials using Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Retrieve the user after successful auth
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail());
        System.out.println("🎫 JWT issued: " + token);

        return token;
    }
}
