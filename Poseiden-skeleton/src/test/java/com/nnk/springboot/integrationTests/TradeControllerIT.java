package com.nnk.springboot.integrationTests;

import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.test_repository.TradeTestH2Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TradeControllerIT {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private TradeTestH2Repository tradeTestH2Repository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }
    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO trade (trade_id, account, type, buy_quantity) VALUES (3, 'tradeAccount', 'tradeType', 56.5 )", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM trade WHERE trade_id = 3", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllTradesShouldReturnTradeListPage() throws Exception {

        mockMvc.perform(get("/api/trade/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tradeList"));

        assertThat(tradeTestH2Repository.findAll().size()).isEqualTo(1);
    }
    @Test
    @WithMockUser("UserTest")
    public void addTradeFormShouldReturnTradeAddPage() throws Exception {
        mockMvc.perform(get("/api/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }
    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "DELETE FROM trade", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addNewTradeShouldReturnTradeListPage() throws Exception {
        TradeDTO newTradeDTO = new TradeDTO();
        newTradeDTO.setAccount("newAccount");
        newTradeDTO.setType("newType");
        newTradeDTO.setBuyQuantity(58.4D);

        mockMvc.perform(post("/api/trade/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("account", newTradeDTO.getAccount())
                        .param("type", newTradeDTO.getType())
                        .param("buyQuantity", String.valueOf(newTradeDTO.getBuyQuantity()))
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/trade/list"));

        assertThat(tradeTestH2Repository.findAll().size()).isEqualTo(1);
        assertThat(tradeTestH2Repository.findAll()).isNotNull();

    }

    @Test
    @WithMockUser("UserTest")
    public void addNewTradeFailedShouldReturnTradeAddPage() throws Exception {
        TradeDTO newTradeDTO = new TradeDTO();
        newTradeDTO.setAccount("newAccount");
        newTradeDTO.setType("newType");
        newTradeDTO.setBuyQuantity(-1D);

        mockMvc.perform(post("/api/trade/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("account", newTradeDTO.getAccount())
                        .param("type", newTradeDTO.getType())
                        .param("buyQuantity", String.valueOf(newTradeDTO.getBuyQuantity()))
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));

        assertThat(tradeTestH2Repository.findAll().size()).isEqualTo(0);

    }
    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO trade (trade_id, account, type, buy_quantity) VALUES (12, 'tradeAccount12', 'tradeType12', 35.1 )", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM trade WHERE trade_id = 12", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void showTradeUpdateForm() throws Exception {
        mockMvc.perform(get("/api/trade/update/{id}", 12)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tradeDTO"))
                .andExpect(view().name("trade/update"));
    }
    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO trade (trade_id, account, type, buy_quantity) VALUES (13, 'tradeAccount13', 'tradeType13', 1987.13 )", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM trade WHERE trade_id = 13", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTradeExistingShouldReturnTradeListPage() throws Exception {
        TradeDTO newTradeDTO = new TradeDTO();
        newTradeDTO.setAccount("AccountUpdate");
        newTradeDTO.setType("TypeUpdated");
        newTradeDTO.setBuyQuantity(1258.4D);
        newTradeDTO.setId(13);

        mockMvc.perform(post("/api/trade/update/{id}",13)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("account", newTradeDTO.getAccount())
                        .param("type", newTradeDTO.getType())
                        .param("buyQuantity", String.valueOf(newTradeDTO.getBuyQuantity()))
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/trade/list"));

        assertThat(tradeTestH2Repository.findAll().size()).isEqualTo(1);
       assertThat(tradeTestH2Repository.findById(13).get().getBuyQuantity()).isEqualTo(1258.4D);

    }

    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO trade (trade_id, account, type, buy_quantity) VALUES (13, 'tradeAccount13', 'tradeType13', 1987.13 )", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM trade WHERE trade_id = 13", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateTradeExistingFailedShouldReturnTradeListPage() throws Exception {
        TradeDTO newTradeDTO = new TradeDTO();
        newTradeDTO.setAccount("AccountUpdate");
        newTradeDTO.setType("TypeUpdated");
        newTradeDTO.setBuyQuantity(-12.5D);
        newTradeDTO.setId(13);

        mockMvc.perform(post("/api/trade/update/{id}",13)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("account", newTradeDTO.getAccount())
                        .param("type", newTradeDTO.getType())
                        .param("buyQuantity", String.valueOf(newTradeDTO.getBuyQuantity()))
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));

        assertThat(tradeTestH2Repository.findAll().size()).isEqualTo(1);
        assertThat(tradeTestH2Repository.findById(13).get().getBuyQuantity()).isEqualTo(1987.13);

    }
    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO trade (trade_id, account, type, buy_quantity) VALUES (8, 'tradeAccount8', 'tradeType8', 88.13 )", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM trade WHERE trade_id = 8", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteTradeExistingShouldReturnTradeListPage() throws Exception {
        mockMvc.perform(get("/api/trade/delete/{id}", 8))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/trade/list"));

        assertAll(
                () -> assertThat(tradeTestH2Repository.findById(8)).isEmpty(),
                () -> assertThat(tradeTestH2Repository.findAll().size()).isEqualTo(0)
        );
    }

}
