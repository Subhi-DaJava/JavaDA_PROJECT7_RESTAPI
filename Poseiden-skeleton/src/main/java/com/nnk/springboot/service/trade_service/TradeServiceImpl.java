package com.nnk.springboot.service.trade_service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.exception.ResourcesNotFoundException;
import com.nnk.springboot.mapper.MapperService;
import com.nnk.springboot.repositories.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * TradeServiceImpl: CRUD
 * @author Subhi
 */
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TradeServiceImpl implements TradeService {
    private static final Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);
    private final TradeRepository tradeRepository;
    private final MapperService mapperService;

    public TradeServiceImpl(TradeRepository tradeRepository, MapperService mapperService) {
        this.tradeRepository = tradeRepository;
        this.mapperService = mapperService;
    }
    /**
     * Get the all Trade from DDB and transfer to TradeDTO
     * @return List of TradeDTO
     */
    @Override
    public List<TradeDTO> getTrades() {
        logger.debug("This getTrades(from TradeServiceImpl) starts here.");
        List<TradeDTO> tradeDTOs;
        List<Trade> trades = tradeRepository.findAll();

        if(trades.isEmpty()){
            logger.info("Trades is empty in DDB!(from getTrades, TradeServiceImpl).");
            return new ArrayList<>();
        }
        logger.info("Trades successfully loaded from DDB(from getTrades, TradeServiceImpl).");
        tradeDTOs = trades.stream().map(trade ->
                mapperService.fromTrade(trade)
        ).collect(Collectors.toList());
        return tradeDTOs;
    }
    /**
     * Find Trade by its Id and return TradeDTO
     * @param id Integer
     * @return TradeDTO
     */
    @Override
    public TradeDTO getTradeById(Integer id) {
        logger.debug("This getTradeById(from TradeServiceImpl) starts here.");
        Trade trade = getTradeByTradeId(id);

        logger.info("Trade successfully found by its id: {} (from getTradeById, TradeServiceImpl).", id);
        return mapperService.fromTrade(trade);
    }
    /**
     * Save a new Trade via TradeDTO
     * @param tradeDTO TradeDTO
     * @return TradeDTO
     */
    @Override
    public TradeDTO saveNewTrade(TradeDTO tradeDTO) {
        logger.debug("This saveNewTrade(from TradeServiceImpl) starts here.");
        Trade trade = mapperService.fromTradeDTO(tradeDTO);
        Trade savedTrade = tradeRepository.save(trade);
        logger.info("New Trade successfully saved into DDB(from saveNewTrade, TradeServiceImpl).");
        TradeDTO returnTradeDTO = mapperService.fromTrade(savedTrade);

        return returnTradeDTO;
    }
    /**
     * Update a Trade if its id exists in DDB
     * @param tradeDTO TradeDTO
     */
    @Override
    public void updateTrade(TradeDTO tradeDTO) {
        logger.debug("This updateTrade method(from TradeServiceImpl) starts here.");
        Trade updateTrade = mapperService.fromTradeDTO(tradeDTO);
        updateTrade.setTradeId(tradeDTO.getId());

        logger.info("Trade which id : {} successfully updated(from updateTrade, TradeServiceImpl).", tradeDTO.getId());
        tradeRepository.save(updateTrade);
    }
    /**
     * Delete a Trade existing in DDB by Trade Id
     * @param id id
     */
    @Override
    public void deleteTradeById(Integer id) {
        logger.debug("This deleteTradeById(from TradeServiceImpl starts here.) ");
        Trade tradeByTradeId = getTradeByTradeId(id);
        if(tradeByTradeId != null) {
            logger.info("Trade which id :{} successfully deleted from DDB(from TradeServiceImpl)", id);
            tradeRepository.deleteById(id);
        }
    }

    private Trade getTradeByTradeId(Integer id) {
        Trade trade = tradeRepository.findById(id).orElseThrow(() -> {
            logger.error("This tradeId:{} not found!", id);
            throw new ResourcesNotFoundException("This Trade doesn't exist with this id : " + id + " , from getTradeByTradeId, TradeServiceImpl.");
        });
        return trade;
    }
}
