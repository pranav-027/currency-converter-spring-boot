package com.miniproject.currency_converter.model.ApiResponse;

import com.miniproject.currency_converter.enums.CurrencyCode;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyConvertResponse {

	private CurrencyCode currency;
	private BigDecimal amount;

}
