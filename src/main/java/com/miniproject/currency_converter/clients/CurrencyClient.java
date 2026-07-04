package com.miniproject.currency_converter.clients;

import com.miniproject.currency_converter.enums.CurrencyCode;
import java.math.BigDecimal;

public interface CurrencyClient {

	BigDecimal getExchangeRate(CurrencyCode baseCurrency, CurrencyCode targetCurrency);

}
