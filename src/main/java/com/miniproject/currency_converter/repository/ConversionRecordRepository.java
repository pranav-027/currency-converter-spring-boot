package com.miniproject.currency_converter.repository;

import com.miniproject.currency_converter.entitiy.ConversionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionRecordRepository extends JpaRepository<ConversionRecord, Long> {

}
