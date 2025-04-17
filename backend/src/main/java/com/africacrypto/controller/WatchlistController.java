package com.africacrypto.controller;

import com.africacrypto.entity.Watchlist;
import com.africacrypto.service.WatchlistService;
import com.africacrypto.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;
    private final JwtUtil jwtUtil;

    private Long extractUserId(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header != null && header.startsWith("Bearer ")
                ? header.substring(7) : null;

        if (token != null) {
            String email = jwtUtil.extractUsername(token);
            // TODO: Replace with actual user lookup by email
            return jwtUtil.extractUserId(token); // You may need to customize this!
        }

        throw new RuntimeException("Unauthorized");
    }

    @PostMapping
    public ResponseEntity<?> addToWatchlist(@RequestBody Map<String, String> body,
                                            HttpServletRequest request) {
        String coinId = body.get("coinId");
        Long userId = extractUserId(request);
        watchlistService.addCoin(userId, coinId);
        return ResponseEntity.ok(Map.of("message", coinId + " added to watchlist"));
    }

    @GetMapping
    public ResponseEntity<List<Watchlist>> getWatchlist(HttpServletRequest request) {
        Long userId = extractUserId(request);
        return ResponseEntity.ok(watchlistService.getUserWatchlist(userId));
    }

    @DeleteMapping("/{coinId}")
    public ResponseEntity<?> removeFromWatchlist(@PathVariable String coinId,
                                                 HttpServletRequest request) {
        Long userId = extractUserId(request);
        watchlistService.removeCoin(userId, coinId);
        return ResponseEntity.ok(Map.of("message", coinId + " removed from watchlist"));
    }
}
