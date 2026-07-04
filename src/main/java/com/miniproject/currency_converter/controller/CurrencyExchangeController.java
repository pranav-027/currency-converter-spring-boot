package com.miniproject.currency_converter.controller;

import com.miniproject.currency_converter.factory.RestResponseFactory;
import com.miniproject.currency_converter.model.ApiResponse.CurrencyConvertResponse;
import com.miniproject.currency_converter.service.CurrencyConvertService;
import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CurrencyExchangeController {

	private final CurrencyConvertService currencyConvertService;

	public CurrencyExchangeController(CurrencyConvertService currencyConvertService) {
		this.currencyConvertService = currencyConvertService;
	}

	@GetMapping("/convertCurrency")
	public ResponseEntity<CurrencyConvertResponse> convertCurrencies(@RequestParam String fromCurrency, @RequestParam String toCurrency, @RequestParam BigDecimal amount) {
		return RestResponseFactory.buildOkResponse(currencyConvertService.convertCurrency(fromCurrency, toCurrency, amount));
	}

}
