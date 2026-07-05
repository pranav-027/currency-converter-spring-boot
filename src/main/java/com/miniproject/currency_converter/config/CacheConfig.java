package com.miniproject.currency_converter.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	public CacheManager cacheManager(){
		CaffeineCacheManager cacheManager = new CaffeineCacheManager("exchangeRates");
		cacheManager.setCaffeine(Caffeine.newBuilder()
				.expireAfterWrite(Duration.ofMinutes(10))
				.maximumSize(100)
				.recordStats());

		return cacheManager;
	}

}
