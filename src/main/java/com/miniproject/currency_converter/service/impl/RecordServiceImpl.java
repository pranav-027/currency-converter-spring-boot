package com.miniproject.currency_converter.service.impl;

import com.miniproject.currency_converter.entitiy.ConversionRecord;
import com.miniproject.currency_converter.enums.CurrencyCode;
import com.miniproject.currency_converter.repository.ConversionRecordRepository;
import com.miniproject.currency_converter.service.RecordService;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class RecordServiceImpl implements RecordService {

	private final ConversionRecordRepository conversionRecordRepository;

	public RecordServiceImpl(ConversionRecordRepository conversionRecordRepository) {
		this.conversionRecordRepository = conversionRecordRepository;
	}

	@Override
	@Transactional
	public void saveRecord(CurrencyCode fromCurrency, CurrencyCode toCurrency, BigDecimal amount, BigDecimal convertedAmount) {
		ConversionRecord record = ConversionRecord.builder()
				.baseCurrency(fromCurrency)
				.targetCurrency(toCurrency)
				.amount(amount)
				.convertedAmount(convertedAmount)
				.build();

		log.info("Saving conversion record: {}", record);
		conversionRecordRepository.save(record);
		log.info("Conversion record saved successfully with ID: {}", record.getId());
	}
}
