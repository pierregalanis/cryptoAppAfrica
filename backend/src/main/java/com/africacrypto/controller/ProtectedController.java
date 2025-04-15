package com.africacrypto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProtectedController {

    @GetMapping("/me")
    public ResponseEntity<String> me() {
        System.out.println("🔒 /api/me was accessed");
        return ResponseEntity.ok("✅ Hello, authenticated user!");
    }
}
