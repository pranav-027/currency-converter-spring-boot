package com.miniproject.currency_converter.clients.impl;

import com.miniproject.currency_converter.clients.CurrencyClient;
import com.miniproject.currency_converter.enums.CurrencyCode;
import com.miniproject.currency_converter.exceptions.BadRequestException;
import com.miniproject.currency_converter.exceptions.TechnicalException;
import com.miniproject.currency_converter.model.ExchangeRateResponse;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
public class CurrencyClientImpl implements CurrencyClient {

	@Value("${currency.api.key}")
	private String API_KEY;

	private final RestClient currencyApiClient;

	public CurrencyClientImpl(RestClient currencyApiClient) {
		this.currencyApiClient = currencyApiClient;
	}

	@Override
	@Cacheable(value = "exchangeRates", key = "#baseCurrency.name() + '-' + #targetCurrency.name()", unless = "#result == null")
	public BigDecimal getExchangeRate(CurrencyCode baseCurrency, CurrencyCode targetCurrency) {
		log.info("Fetching exchange rate for base currency: {} and target currency: {}", baseCurrency, targetCurrency);
	ExchangeRateResponse exchangeRateResponse = currencyApiClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/")
						.queryParam("apikey",API_KEY)
						.queryParam("base_currency", baseCurrency.name())
						.queryParam("currencies", targetCurrency.name())
						.build())
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
					log.error("Client error occurred while fetching exchange rate: {}", res.getStatusCode());
					throw new BadRequestException(res.getBody().toString());
				})
				.onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
					log.error("Server error occurred while fetching exchange rate: {}", res.getStatusCode());
					throw new TechnicalException("Server error occurred while fetching exchange rate");
				})
				.body(new ParameterizedTypeReference<>() {
				});

		log.info("Exchange Rate Response: {}", exchangeRateResponse);

		if (exchangeRateResponse == null || exchangeRateResponse.getData() == null) {
			throw new TechnicalException("Invalid response from the API");
		}

		log.info("Exchange rate response received: {}", exchangeRateResponse);
		return exchangeRateResponse.getData().values().stream().findFirst().orElseThrow(() -> new TechnicalException("Exchange rate not found in the response"));
	}


}
