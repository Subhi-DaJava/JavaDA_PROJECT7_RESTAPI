package com.nnk.springboot.service.trade_service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;

import java.util.List;

/**
 * TradeService allows to insert the business logic
 * in the Trade business domain.
 * @author Subhi
 */
public interface TradeService {
    List<TradeDTO> getTrades();
    TradeDTO getTradeById(Integer id);
    TradeDTO saveNewTrade(TradeDTO tradeDTO);
    void updateTrade(TradeDTO tradeDTO);
    void deleteTradeById(Integer id);
}
