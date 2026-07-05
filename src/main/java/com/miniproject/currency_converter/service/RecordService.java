package com.miniproject.currency_converter.service;

import com.miniproject.currency_converter.enums.CurrencyCode;
import java.math.BigDecimal;

public interface RecordService {

	void saveRecord(CurrencyCode baseCurrency, CurrencyCode targetCurrency, BigDecimal amount, BigDecimal convertedAmount);

}
