package com.africacrypto.controller;

import com.africacrypto.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/promote-to-admin")
    public ResponseEntity<String> promote(@RequestParam String email) {
        userService.promoteToAdmin(email);
        return ResponseEntity.ok("✅ " + email + " promoted to ADMIN");
    }
}
