package com.miniproject.currency_converter.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
@RequiredArgsConstructor
public class DebugController {

    private final CacheManager cacheManager;

    @GetMapping("/debug/cache/{cacheName}")
    public Map<Object, Object> viewCache(@PathVariable String cacheName) {
        CaffeineCache springCache = (CaffeineCache) cacheManager.getCache(cacheName);
        if (springCache == null) {
            throw new IllegalArgumentException("No cache found with name: " + cacheName);
        }
        return springCache.getNativeCache().asMap();
    }
}
