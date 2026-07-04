package com.miniproject.currency_converter.model;

import com.miniproject.currency_converter.enums.CurrencyCode;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;

@Data
public class ExchangeRateResponse {

    private Map<CurrencyCode, BigDecimal> data;

}
