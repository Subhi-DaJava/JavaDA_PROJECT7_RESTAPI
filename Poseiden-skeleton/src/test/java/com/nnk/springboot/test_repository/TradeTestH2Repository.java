package com.nnk.springboot.test_repository;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeTestH2Repository extends JpaRepository<Trade, Integer> {
}
