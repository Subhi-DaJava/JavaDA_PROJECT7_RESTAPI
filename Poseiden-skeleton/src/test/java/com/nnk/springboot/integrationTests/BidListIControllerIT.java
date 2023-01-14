package com.nnk.springboot.integrationTests;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.test_repository.BidListTestH2Repository;
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
public class BidListIControllerIT {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private BidListTestH2Repository bidListTestH2Repository;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    //@WithMockUser(value = "UserTest", authorities = {"USER", "ADMIN"})
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO bidlist (bid_list_id, account, type, bid_quantity) VALUES (20, 'NewAccount', 'NewType', 35)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM bidlist WHERE bid_list_id = 20", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllBidListsShouldReturnHomePage() throws Exception {
        mockMvc.perform(get("/api/bidList/list").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bidList"))
                .andExpect(view().name("bidList/list"));

        assertThat(bidListTestH2Repository.findAll().size()).isEqualTo(1);

    }

    @Test
    @WithMockUser("UserTest")
    public void addBidListFormShouldReturnBidListAddPage() throws Exception {
        mockMvc.perform(get("/api/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser("UserTest")
    public void addNewBidListShouldRedirectBidListPage() throws Exception {
        BidListDTO bidListDTO = new BidListDTO("Account", "Type", 562.0);

        mockMvc.perform(post("/api/bidList/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("account", bidListDTO.getAccount())
                        .param("type", bidListDTO.getType())
                        .param("bidQuantity", String.valueOf(bidListDTO.getBidQuantity()))
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/bidList/list"));

        assertThat(bidListTestH2Repository.findAll().size()).isEqualTo(1);
        assertThat(bidListTestH2Repository.findAll()).isNotNull();
    }

    @Test
    @WithMockUser("UserTest")
    public void addNewBidListFailedShouldReturnBidListAddPage() throws Exception {

        mockMvc.perform(post("/api/bidList/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));

        assertThat(bidListTestH2Repository.findAll().size()).isEqualTo(0);
    }

    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO bidlist (bid_list_id, account, type, bid_quantity) VALUES (20, 'NewAccount', 'NewType', 35)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM bidlist WHERE bid_list_id = 20", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void showUpdateFormPage() throws Exception {
        mockMvc.perform(get("/api/bidList/update/{id}", 20)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bidListDTO"))
                .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO bidlist (bid_list_id, account, type, bid_quantity) VALUES (25, 'NewAccount', 'NewType', 35.0)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM bidlist where bid_list_id = 25", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateBidListShouldReturnBidListPage() throws Exception {
        BidListDTO bidListDTO = new BidListDTO("AccountUpdate", "TypeUpdate", 562.0);
        bidListDTO.setBidId(25);

        mockMvc.perform(post("/api/bidList/update/{id}", 25)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("account", bidListDTO.getAccount())
                        .param("type", bidListDTO.getType())
                        .param("bidQuantity", String.valueOf(bidListDTO.getBidQuantity()))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/bidList/list"));

        assertThat(bidListTestH2Repository.findById(25).get().getType()).isEqualTo("TypeUpdate");
    }

    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO bidlist (bid_list_id, account, type, bid_quantity) VALUES (25, 'NewAccount', 'NewType', 35.0)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM bidlist where bid_list_id = 25", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateBidFailedShouldReturnBidListUpdatePage() throws Exception {

        mockMvc.perform(post("/api/bidList/update/{id}", 25)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));

    }

    @Test
    @WithMockUser
    @Sql(statements = "INSERT INTO bidlist (bid_list_id, account, type, bid_quantity) VALUES (8, 'NewAccount', 'NewType', 35.0)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM bidlist where bid_list_id = 8", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteBidListExistingShouldReturnBidListPage() throws Exception {

        mockMvc.perform(get("/api/bidList/delete/{id}", 8))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/bidList/list"));

        assertAll(
                () -> assertThat(bidListTestH2Repository.findById(8)).isEmpty(),
                () -> assertThat(bidListTestH2Repository.findAll().size()).isEqualTo(0)
        );
    }

}
