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

import java.util.Set;

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
                .enabled(true)
                .kycVerified(false)
                .roles(Set.of("USER")) // 🔐 default role
                .build();

        User saved = userRepository.save(user);

        System.out.println("💾 Saved user with ID: " + saved.getId());

        return UserDTO.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .email(saved.getEmail())
                .role("USER")
                .build();
    }

    @Override
    public String login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Updated to pass both email and userId to generateToken
        return jwtUtil.generateToken(user.getEmail(), user.getId());
    }

    @Override
    public void promoteToAdmin(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getRoles().add("ADMIN"); // 🔥 add ADMIN to existing roles
        userRepository.save(user);

        System.out.println("🚀 Promoted " + email + " to ADMIN");
    }
}
