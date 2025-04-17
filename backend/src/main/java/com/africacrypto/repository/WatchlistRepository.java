package com.africacrypto.repository;

import com.africacrypto.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findByUserId(Long userId);
    void deleteByUserIdAndCoinId(Long userId, String coinId);
    boolean existsByUserIdAndCoinId(Long userId, String coinId);
}
