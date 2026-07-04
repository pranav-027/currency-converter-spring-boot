package com.miniproject.currency_converter.service;

import com.miniproject.currency_converter.model.ApiResponse.CurrencyConvertResponse;
import java.math.BigDecimal;

public interface CurrencyConvertService {

	CurrencyConvertResponse convertCurrency(String fromCurrency, String toCurrency, BigDecimal amount);

}
