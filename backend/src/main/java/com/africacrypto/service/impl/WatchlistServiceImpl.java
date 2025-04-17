package com.africacrypto.service.impl;

import com.africacrypto.entity.Watchlist;
import com.africacrypto.repository.WatchlistRepository;
import com.africacrypto.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WatchlistServiceImpl implements WatchlistService {

    private final WatchlistRepository watchlistRepository;

    @Override
    public void addCoin(Long userId, String coinId) {
        if (!watchlistRepository.existsByUserIdAndCoinId(userId, coinId)) {
            Watchlist item = Watchlist.builder()
                    .userId(userId)
                    .coinId(coinId)
                    .addedAt(LocalDateTime.now())
                    .build();
            watchlistRepository.save(item);
        }
    }

    @Override
    public List<Watchlist> getUserWatchlist(Long userId) {

        return watchlistRepository.findByUserId(userId);
    }

    @Override
    public void removeCoin(Long userId, String coinId) {
        watchlistRepository.deleteByUserIdAndCoinId(userId, coinId);
    }
}
