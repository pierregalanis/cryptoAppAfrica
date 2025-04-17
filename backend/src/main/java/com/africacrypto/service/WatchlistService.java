package com.africacrypto.service;

import com.africacrypto.entity.Watchlist;

import java.util.List;

public interface WatchlistService {
    void addCoin(Long userId, String coinId);
    List<Watchlist> getUserWatchlist(Long userId);
    void removeCoin(Long userId, String coinId);
}
