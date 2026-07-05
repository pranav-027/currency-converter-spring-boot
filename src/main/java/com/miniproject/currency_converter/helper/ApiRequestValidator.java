package com.miniproject.currency_converter.helper;

import com.miniproject.currency_converter.enums.CurrencyCode;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class ApiRequestValidator {


	public void validateCurrencyConvertRequest(String fromCurrency, String toCurrency, BigDecimal amount) {
		if (fromCurrency == null || fromCurrency.isEmpty()) {
			throw new IllegalArgumentException("From currency is required");
		}
		if (toCurrency == null || toCurrency.isEmpty()) {
			throw new IllegalArgumentException("To currency is required");
		}
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero");
		}

		if (CurrencyCode.fromCode(fromCurrency.toUpperCase()) == null || CurrencyCode.fromCode(toCurrency.toUpperCase()) == null) {
			throw new IllegalArgumentException("Invalid currency code");
		}

	}
}
