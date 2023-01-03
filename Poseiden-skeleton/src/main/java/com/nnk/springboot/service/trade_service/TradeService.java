package com.nnk.springboot.service.trade_service;

import com.nnk.springboot.domain.Trade;

import java.util.List;

/**
 * TradeService allows to insert the business logic
 * in the Trade business domain.
 * @author Subhi
 */
public interface TradeService {
    List<Trade> getTrades();
    Trade getTradeById(Integer id);
    Trade saveNewTrade(Trade trade);
    void updateTrade(Trade trade);
    void deleteTradeById(Integer id);
}
