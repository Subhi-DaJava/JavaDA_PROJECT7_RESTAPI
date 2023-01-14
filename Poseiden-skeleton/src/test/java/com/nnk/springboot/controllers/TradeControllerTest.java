package com.nnk.springboot.controllers;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.service.trade_service.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = TradeController.class)
class TradeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TradeService tradeService;

    private List<TradeDTO> tradeList;
    @BeforeEach
    void init() {
        TradeDTO tradeDTO = new TradeDTO();
        tradeDTO.setType("trade_type");
        tradeDTO.setAccount("trade_account");
        tradeDTO.setBuyQuantity(345D);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        tradeList = new ArrayList<>(List.of(tradeDTO));

    }
    @Test
    @WithMockUser
    void home() throws Exception {
        when(tradeService.getTrades()).thenReturn(tradeList);

        mockMvc.perform(get("/api/trade/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("tradeList", tradeList))
                .andExpect(view().name("trade/list"));
    }

    @Test
    @WithMockUser
    void addTradeForm() throws Exception {
        mockMvc.perform(get("/api/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser
    void validate() throws Exception {
        TradeDTO tradeDTO = new TradeDTO();
        tradeDTO.setType("new_type");
        tradeDTO.setAccount("new_account");
        tradeDTO.setBuyQuantity(412D);

        when(tradeService.saveNewTrade(tradeDTO)).thenReturn(tradeDTO);

        mockMvc.perform(post("/api/trade/validate")
                .param("account", "new_account")
                .param("type", "new_type")
                .param("buyQuantity", String.valueOf(412D))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/trade/list"));

    }
    @Test
    @WithMockUser
    void validateFailed() throws Exception {
        mockMvc.perform(post("/api/trade/validate"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser
    void showUpdateForm() throws Exception {
        TradeDTO tradeUpdate = new TradeDTO();
        tradeUpdate.setType("update_type");
        tradeUpdate.setAccount("update_account");
        tradeUpdate.setBuyQuantity(75D);
        tradeUpdate.setId(4);

        when(tradeService.getTradeById(anyInt())).thenReturn(tradeUpdate);

        mockMvc.perform(get("/api/trade/update/{id}", 4))
                .andExpect(model().attribute("tradeDTO", tradeUpdate))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @Test
    @WithMockUser
    void updateTradeFailed() throws Exception {
        mockMvc.perform(post("/api/trade/update/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }
    @Test
    @WithMockUser
    void updateTrade() throws Exception {
        TradeDTO tradeUpdate = new TradeDTO();
        tradeUpdate.setType("updateType");
        tradeUpdate.setAccount("updateAccount");
        tradeUpdate.setBuyQuantity(985D);
        tradeUpdate.setId(3);

        doNothing().when(tradeService).updateTrade(tradeUpdate);

        mockMvc.perform(post("/api/trade/update/{id}", 3)
                .param("account", "updateAccount")
                .param("type", "updateType")
                .param("buyQuantity", String.valueOf(985D))
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/trade/list"));
    }

    @Test
    @WithMockUser
    void deleteTrade() throws Exception {

        doNothing().when(tradeService).deleteTradeById(anyInt());

        mockMvc.perform(get("/api/trade/delete/{id}", 4))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/trade/list"));
    }
}