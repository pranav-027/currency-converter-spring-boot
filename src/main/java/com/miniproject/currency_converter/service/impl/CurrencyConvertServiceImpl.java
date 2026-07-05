package com.miniproject.currency_converter.service.impl;

import com.miniproject.currency_converter.clients.CurrencyClient;
import com.miniproject.currency_converter.enums.CurrencyCode;
import com.miniproject.currency_converter.exceptions.TechnicalException;
import com.miniproject.currency_converter.helper.ApiRequestValidator;
import com.miniproject.currency_converter.model.ApiResponse.CurrencyConvertResponse;
import com.miniproject.currency_converter.service.CurrencyConvertService;
import com.miniproject.currency_converter.service.RecordService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CurrencyConvertServiceImpl implements CurrencyConvertService {

	private final CurrencyClient currencyClient;
	private final ApiRequestValidator apiRequestValidator;
	private final RecordService recordService;

	public CurrencyConvertServiceImpl(CurrencyClient currencyClient,
			ApiRequestValidator apiRequestValidator, RecordService recordService) {
		this.currencyClient = currencyClient;
		this.apiRequestValidator = apiRequestValidator;
		this.recordService = recordService;
	}

	@Override
	public CurrencyConvertResponse convertCurrency(String fromCurrency, String toCurrency, BigDecimal amount) {
		log.debug("Converting currency from {} to {} for amount {}", fromCurrency, toCurrency, amount);
		apiRequestValidator.validateCurrencyConvertRequest(fromCurrency, toCurrency, amount);
		BigDecimal exchangeRate = currencyClient.getExchangeRate(CurrencyCode.fromCode(fromCurrency.toUpperCase()), CurrencyCode.fromCode(toCurrency.toUpperCase()));
		if (exchangeRate == null) {
			log.error("Exchange rate not found for {} to {}", fromCurrency, toCurrency);
			throw new TechnicalException("Exchange rate not found for " + fromCurrency + " to " + toCurrency);
		}
		log.info("Exchange rate for {} to {} is {}", fromCurrency, toCurrency, exchangeRate);
		BigDecimal convertedAmt = amount.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);

		recordService.saveRecord(CurrencyCode.fromCode(fromCurrency.toUpperCase()), CurrencyCode.fromCode(toCurrency.toUpperCase()), amount, convertedAmt);

		return CurrencyConvertResponse.builder()
				.currency(CurrencyCode.fromCode(toCurrency.toUpperCase()))
				.amount(convertedAmt)
				.build();
	}
}
