package com.nnk.springboot.service.trade_service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.mapper.MapperService;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradeServiceImplTest {
    @InjectMocks
    private TradeServiceImpl tradeService;
    @Mock
    private TradeRepository tradeRepository;
    @Mock
    private MapperService mapperService;

    @Test
    void getTrades() {
        TradeDTO tradeDTO1 = new TradeDTO();
        TradeDTO tradeDTO2 = new TradeDTO();


        Trade trade1 = new Trade(15, "account1", "type1", 300D);
        Trade trade2 = new Trade(20, "account2", "type1", 650D);

        List<Trade> trades = new ArrayList<>(List.of(trade1, trade2));

        when(mapperService.fromTrade(trade1)).thenReturn(tradeDTO1);
        when(mapperService.fromTrade(trade2)).thenReturn(tradeDTO2);

        tradeDTO1.setId(trade1.getTradeId());
        tradeDTO1.setAccount(trade1.getAccount());
        tradeDTO1.setType(trade1.getType());
        tradeDTO1.setBuyQuantity(trade1.getBuyQuantity());

        when(tradeRepository.findAll()).thenReturn(trades);

        List<TradeDTO> tradeDTOList = tradeService.getTrades();

        assertThat(tradeDTOList.size()).isEqualTo(2);
        assertThat(tradeDTOList.get(0).getAccount()).isEqualTo(trade1.getAccount());
        assertThat(tradeDTOList).isNotNull();
    }

    @Test
    void getEmptyTradeList() {
        List<Trade> trades = new ArrayList<>();
        when(tradeRepository.findAll()).thenReturn(trades);

        List<TradeDTO> tradeDTOList = tradeService.getTrades();

        assertThat(tradeDTOList.size()).isEqualTo(0);
    }

    @Test
    void getTradeById() {
        TradeDTO tradeDTO = new TradeDTO();
        Trade trade = new Trade(21,"account", "type", 856D);

        //this mock will map any Trade to TradeDTO
        when(mapperService.fromTrade(any())).thenReturn(tradeDTO);
        tradeDTO.setId(trade.getTradeId());
        tradeDTO.setType(trade.getType());

        when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(trade));

        TradeDTO tradeDTOGetByTradeId = tradeService.getTradeById(trade.getTradeId());

        assertThat(tradeDTOGetByTradeId.getId()).isEqualTo(trade.getTradeId());
        assertThat(tradeDTOGetByTradeId.getType()).isEqualTo(trade.getType());
    }

    @Test
    void getTradeByNotExistingId() {
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tradeService.getTradeById(anyInt()));
    }

    @Test
    void saveNewTrade() {
        TradeDTO tradeDTO= new TradeDTO();
        Trade trade = new Trade("account", "type", 856D);
        //this mock will map any Trade to TradeDTO
        when(mapperService.fromTrade(any())).thenReturn(tradeDTO);

        tradeDTO.setType(trade.getType());
        tradeDTO.setAccount(trade.getAccount());
        tradeDTO.setBuyQuantity(trade.getBuyQuantity());


        when(mapperService.fromTradeDTO(any())).thenReturn(trade);
        when(tradeRepository.save(any())).thenReturn(trade);

        TradeDTO tradeSaved = tradeService.saveNewTrade(tradeDTO);

        assertThat(tradeSaved.getType()).isEqualTo(trade.getType());
        verify(tradeRepository, times(1)).save(any());
        verify(mapperService, times(1)).fromTrade(any());
        verify(mapperService, times(1)).fromTradeDTO(any());
    }

    @Test
    void updateTrade() {
        Trade trade = new Trade();
        TradeDTO tradeDTO= new TradeDTO();

        when(mapperService.fromTradeDTO(any())).thenReturn(trade);
        trade = new Trade("account", "type", 700D);


        when(tradeRepository.save(any())).thenReturn(trade);

        when(mapperService.fromTradeDTO(any())).thenReturn(trade);

        trade.setType("type_updated");
        trade.setAccount("account_updated");
        trade.setTradeId(23);

        tradeDTO.setType("type_updated");
        tradeDTO.setAccount("account_updated");
        tradeDTO.setId(23);

        tradeService.updateTrade(tradeDTO);

        assertThat(trade.getType()).isEqualTo(tradeDTO.getType());
        assertThat(trade.getAccount()).isEqualTo("account_updated");
        assertThat(trade.getTradeId()).isEqualTo(23);
    }

    @Test
    void deleteTradeById() {
        Trade trade = new Trade(3,"new_account", "new_type", 235D);

        when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(trade));
        doNothing().when(tradeRepository).deleteById(anyInt());

        tradeService.deleteTradeById(anyInt());

        verify(tradeRepository,times(1)).deleteById(anyInt());
        verify(tradeRepository, times(1)).findById(anyInt());
        assertThat(tradeService.getTradeById(trade.getTradeId())).isNull();
    }
}