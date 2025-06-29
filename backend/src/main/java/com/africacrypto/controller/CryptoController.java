package com.africacrypto.controller;

import com.africacrypto.service.external.CoinGeckoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/crypto")
@RequiredArgsConstructor
public class CryptoController {

    private final CoinGeckoService coinGeckoService;

    // ✅ Simple health check
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {

        return ResponseEntity.ok("pong");
    }

    // ✅ Flattened price response for selected coins
    @GetMapping("/prices")
    public ResponseEntity<Map<String, Double>> getCryptoPrices(
            @RequestParam(defaultValue = "usd") String currency,
            @RequestParam(defaultValue = "bitcoin,ethereum") String ids
    ) {
        System.out.println("🪙 [Controller] /api/crypto/prices called");
        System.out.println("📥 ids: " + ids);
        System.out.println("📥 currency: " + currency);

        String[] cryptoArray = ids.split(",");
        Map<String, Map<String, ?>> response = coinGeckoService.getPrices(cryptoArray, currency);

        if (response == null || response.isEmpty()) {
            System.out.println("⚠️ Empty or null response from CoinGeckoService.");
            return ResponseEntity.ok(Map.of("debug", -1.0));
        }

        Map<String, Double> flatPrices = new HashMap<>();
        for (Map.Entry<String, Map<String, ?>> entry : response.entrySet()) {
            String coin = entry.getKey();
            Map<String, ?> priceMap = entry.getValue();

            if (priceMap != null && priceMap.containsKey(currency)) {
                Object rawValue = priceMap.get(currency);
                try {
                    Double value = Double.parseDouble(rawValue.toString());
                    flatPrices.put(coin, value);
                } catch (NumberFormatException e) {
                    System.out.println("❌ Failed to parse value for " + coin + ": " + rawValue);
                }
            }
        }

        System.out.println("✅ Final flattened result: " + flatPrices);
        return ResponseEntity.ok(flatPrices);
    }

    // ✅ Full market data for top 1000 coins
    @GetMapping("/markets/top")
    public ResponseEntity<List<Map<String, Object>>> getTop1000Coins(
            @RequestParam(defaultValue = "usd") String currency
    ) {
        List<Map<String, Object>> coins = coinGeckoService.getTop1000Coins(currency);
        return ResponseEntity.ok(coins);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Map<String, Object>>> searchCoins(
            @RequestParam String query
    ) {
        List<Map<String, Object>> results = coinGeckoService.searchCoins(query);
        return ResponseEntity.ok(results);
    }

}
