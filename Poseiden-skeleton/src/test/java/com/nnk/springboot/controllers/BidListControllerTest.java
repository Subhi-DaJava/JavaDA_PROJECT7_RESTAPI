package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.service.bid_service.BidListService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = BidListController.class)
class BidListControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private BidListService bidListService;

    private List<BidListDTO> bidList;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        bidList = new ArrayList<>(List.of(
                new BidListDTO("Account1", "Type1", 2560D),
                new BidListDTO("Account2", "Type2", 432D)));
    }
    @Test
    @WithMockUser
    void home() throws Exception {

        when(bidListService.getBidList()).thenReturn(bidList);

        mockMvc.perform(get("/api/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("bidList", bidList))
                .andExpect(view().name("bidList/list"));

    }

    @Test
    @WithMockUser
    void addBidForm() throws Exception {
        mockMvc.perform(get("/api/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser
    void validateFailed() throws Exception {

        mockMvc.perform(post("/api/bidList/validate"))
                .andExpect(status().isOk())
                .andExpect(view().name( "bidList/add"));

    }
    @Test
    @WithMockUser
    void validate() throws Exception {
        BidListDTO bidListDTO = new BidListDTO("Account", "Type", 562D);

        when(bidListService.saveNewBidList(bidListDTO)).thenReturn(bidListDTO);

        mockMvc.perform(post("/api/bidList/validate")
                .param("account", "newAccount")
                .param("type", "newType")
                .param("bidQuantity", String.valueOf(526D))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name( "redirect:/api/bidList/list"));

    }

    @Test
    @WithMockUser
    void showUpdateForm() throws Exception {
        BidListDTO bidListDTO = new BidListDTO("Account", "Type", 320D);

        when(bidListService.getBidListById(anyInt())).thenReturn(bidListDTO);

        mockMvc.perform(get("/api/bidList/update/{id}", 2))
                .andExpect(status().isOk())
                .andExpect(model().attribute("bidListDTO", bidListDTO))
                .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithMockUser
    void updateBid() throws Exception {
        BidListDTO bidListDTO = new BidListDTO("Account", "Type", 147D);
        bidListDTO.setBidId(3);
        doNothing().when(bidListService).updateBidList(bidListDTO);

        mockMvc.perform(post("/api/bidList/update/{id}", 3)
                .param("account", "new_account")
                .param("type", "new_type")
                .param("bidQuantity", String.valueOf(235D))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name( "redirect:/api/bidList/list"));
    }
    @Test
    @WithMockUser("UserTest")
    void updateBidFailed() throws Exception {
        mockMvc.perform(post("/api/bidList/update/{id}", 7))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithMockUser
    void deleteBid() throws Exception {
        doNothing().when(bidListService).deleteBidListById(5);

        mockMvc.perform(get("/api/bidList/delete/{id}", 5))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/bidList/list"));
    }
}