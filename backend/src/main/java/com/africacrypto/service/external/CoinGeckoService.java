package com.africacrypto.service.external;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    // ✅ Quick price lookup (used by /api/crypto/prices)
    public Map<String, Map<String, ?>> getPrices(String[] cryptoIds, String currency) {
        String joinedIds = String.join(",", cryptoIds);

        String url = UriComponentsBuilder.fromHttpUrl("https://api.coingecko.com/api/v3/simple/price")
                .queryParam("ids", joinedIds)
                .queryParam("vs_currencies", currency)
                .toUriString();

        log.info("📡 CoinGecko API URL: {}", url);

        try {
            Map<String, Map<String, ?>> response = restTemplate.getForObject(url, Map.class);
            log.info("✅ Raw CoinGecko response: {}", response);
            return response != null ? response : Map.of();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("❌ CoinGecko call failed", e);
            return Map.of();
        }
    }

    // ✅ Full market data for top 1000 coins (used by /api/crypto/markets/top)
    public List<Map<String, Object>> getTop1000Coins(String currency) {
        List<Map<String, Object>> allCoins = new ArrayList<>();
        int perPage = 250;
        int maxPages = 4; // 250 × 4 = 1000

        for (int page = 1; page <= maxPages; page++) {
            String url = UriComponentsBuilder.fromHttpUrl("https://api.coingecko.com/api/v3/coins/markets")
                    .queryParam("vs_currency", currency)
                    .queryParam("order", "market_cap_desc")
                    .queryParam("per_page", perPage)
                    .queryParam("page", page)
                    .queryParam("sparkline", false)
                    .toUriString();

            log.info("📊 Fetching page {} from: {}", page, url);

            try {
                List<Map<String, Object>> pageData = restTemplate.getForObject(url, List.class);
                if (pageData == null || pageData.isEmpty()) {
                    log.info("✅ Page {} returned empty. Stopping early.", page);
                    break;
                }
                allCoins.addAll(pageData);
            } catch (Exception e) {
                log.error("❌ Failed to fetch page {}", page, e);
                break;
            }
        }

        log.info("✅ Total coins fetched: {}", allCoins.size());
        return allCoins;
    }

    public List<Map<String, Object>> searchCoins(String query) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.coingecko.com/api/v3/search")
                .queryParam("query", query)
                .toUriString();

        log.info("🔍 Searching coins with query: {}", query);

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            List<Map<String, Object>> coins = (List<Map<String, Object>>) response.get("coins");
            log.info("✅ Found {} results", coins != null ? coins.size() : 0);
            return coins != null ? coins : List.of();
        } catch (Exception e) {
            log.error("❌ CoinGecko search failed for query: {}", query, e);
            return List.of();
        }
    }
}
