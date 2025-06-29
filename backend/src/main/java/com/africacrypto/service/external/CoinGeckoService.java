package com.africacrypto.service.external;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CoinGeckoService {

    private static final Logger log = LoggerFactory.getLogger(CoinGeckoService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    // ✅ Quick price lookup
    @CircuitBreaker(name = "coingecko", fallbackMethod = "getPricesFallback")
    @Retry(name = "coingecko")
    @Cacheable(value = "prices", key = "#cryptoIds + '_' + #currency")
    public Map<String, Map<String, ?>> getPrices(String[] cryptoIds, String currency) {
        String joinedIds = String.join(",", cryptoIds);
        String url = UriComponentsBuilder.fromHttpUrl("https://api.coingecko.com/api/v3/simple/price")
                .queryParam("ids", joinedIds)
                .queryParam("vs_currencies", currency)
                .toUriString();

        log.info("📡 CoinGecko API URL: {}", url);
        Map<String, Map<String, ?>> response = restTemplate.getForObject(url, Map.class);
        log.info("✅ Raw CoinGecko response: {}", response);
        return response != null ? response : Map.of();
    }

    public Map<String, Map<String, ?>> getPricesFallback(String[] cryptoIds, String currency, Throwable t) {
        log.error("⚠️ getPrices fallback triggered: {}", t.getMessage());
        return Map.of();
    }

    // ✅ Top 1000 coins market data
    @CircuitBreaker(name = "coingecko", fallbackMethod = "getTop1000CoinsFallback")
    @Retry(name = "coingecko")
    @Cacheable(value = "topCoins", key = "#currency")
    public List<Map<String, Object>> getTop1000Coins(String currency) {
        List<Map<String, Object>> allCoins = new ArrayList<>();
        int perPage = 250;
        int maxPages = 4;

        for (int page = 1; page <= maxPages; page++) {
            String url = UriComponentsBuilder.fromHttpUrl("https://api.coingecko.com/api/v3/coins/markets")
                    .queryParam("vs_currency", currency)
                    .queryParam("order", "market_cap_desc")
                    .queryParam("per_page", perPage)
                    .queryParam("page", page)
                    .queryParam("sparkline", false)
                    .toUriString();

            log.info("📊 Fetching page {} from: {}", page, url);
            List<Map<String, Object>> pageData = restTemplate.getForObject(url, List.class);
            if (pageData == null || pageData.isEmpty()) {
                log.info("✅ Page {} returned empty. Stopping early.", page);
                break;
            }
            allCoins.addAll(pageData);
        }

        log.info("✅ Total coins fetched: {}", allCoins.size());
        return allCoins;
    }

    public List<Map<String, Object>> getTop1000CoinsFallback(String currency, Throwable t) {
        log.error("⚠️ getTop1000Coins fallback triggered: {}", t.getMessage());
        return List.of();
    }

    // ✅ Coin search
    @CircuitBreaker(name = "coingecko", fallbackMethod = "searchCoinsFallback")
    @Retry(name = "coingecko")
    @Cacheable(value = "coinSearch", key = "#query")
    public List<Map<String, Object>> searchCoins(String query) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.coingecko.com/api/v3/search")
                .queryParam("query", query)
                .toUriString();

        log.info("🔍 Searching coins with query: {}", query);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> coins = (List<Map<String, Object>>) response.get("coins");
        log.info("✅ Found {} results", coins != null ? coins.size() : 0);
        return coins != null ? coins : List.of();
    }

    public List<Map<String, Object>> searchCoinsFallback(String query, Throwable t) {
        log.error("⚠️ searchCoins fallback triggered: {}", t.getMessage());
        return List.of();
    }
}
