package com.africacrypto.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_watchlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Watchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;        // From authenticated JWT
    private String coinId;      // CoinGecko ID (e.g. "solana")

    private LocalDateTime addedAt;
}
