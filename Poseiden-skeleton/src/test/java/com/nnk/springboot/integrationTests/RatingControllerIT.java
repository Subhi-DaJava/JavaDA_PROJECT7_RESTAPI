package com.nnk.springboot.integrationTests;

import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.test_repository.RatingTestH2Repository;
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
public class RatingControllerIT {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RatingTestH2Repository ratingTestH2Repository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }
    @Test
    @WithMockUser("TestUser")
    @Sql(statements = "INSERT INTO rating (id, moodys_rating, sand_p_rating, fitch_rating, order_number) VALUES (6, 'moodys_rating_1', 'sandPRating1', 'fitch_rating_1', 325)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM rating WHERE id = 6", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllRatingsShouldReturnRatingListPage() throws Exception {
        mockMvc.perform(get("/api/rating/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ratingList"));

        assertThat(ratingTestH2Repository.findAll().size()).isEqualTo(1);
    }
    @Test
    @WithMockUser("UserTest")
    public void addRatingFormShouldReturnRatingAddPage() throws Exception {
        mockMvc.perform(get("/api/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "DELETE FROM rating", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addNewRatingShouldReturnRatingListPage() throws Exception {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setMoodysRating("MoodysRating");
        ratingDTO.setSandPRating("SandPRating");
        ratingDTO.setFitchRating("FitchingRating");
        ratingDTO.setOrderNumber(125);

        mockMvc.perform(post("/api/rating/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("moodysRating", ratingDTO.getMoodysRating())
                        .param("fitchRating", ratingDTO.getFitchRating())
                        .param("sandPRating", ratingDTO.getSandPRating())
                        .param("orderNumber", String.valueOf(ratingDTO.getOrderNumber()))
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/rating/list"));

        assertThat(ratingTestH2Repository.findAll().size()).isEqualTo(1);
        assertThat(ratingTestH2Repository.findAll()).isNotNull();
    }

    @Test
    @WithMockUser("UserTest")
    public void addNewRatingFailedShouldReturnRatingAddPage() throws Exception {
        mockMvc.perform(post("/api/rating/validate")
                        .param("moodysRating", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));

        assertThat(ratingTestH2Repository.findAll().size()).isEqualTo(0);
    }
    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO rating (id, moodys_rating, sand_p_rating, fitch_rating, order_number) VALUES (8, 'moodys_rating_1', 'sandPRating1', 'fitch_rating_1', 325)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM rating WHERE id = 8", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void showRatingUpdateForm() throws Exception {
        mockMvc.perform(get("/api/rating/update/{id}", 8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ratingDTO"))
                .andExpect(view().name("rating/update"));
    }
    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO rating (id, moodys_rating, sand_p_rating, fitch_rating, order_number) VALUES (10, 'moodys_rating_1', 'sandPRating1', 'fitch_rating_1', 325)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM rating WHERE id = 10", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void UpdateRatingExistingShouldReturnRatingListPage() throws Exception {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setMoodysRating("MoodysUpdated");
        ratingDTO.setSandPRating("SandUpdated");
        ratingDTO.setFitchRating("FitchUpdated");
        ratingDTO.setOrderNumber(125);
        ratingDTO.setRatingID(10);

        mockMvc.perform(post("/api/rating/update/{id}", 10)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("moodysRating", ratingDTO.getMoodysRating())
                        .param("sandPRating", ratingDTO.getSandPRating())
                        .param("fitchRating", ratingDTO.getFitchRating())
                        .param("orderNumber", String.valueOf(ratingDTO.getOrderNumber()))
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/rating/list"));

        assertThat(ratingTestH2Repository.findById(10).get().getFitchRating()).isEqualTo("FitchUpdated");
    }

    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO rating (id, moodys_rating, sand_p_rating, fitch_rating, order_number) VALUES (10, 'moodys_rating_1', 'sandPRating1', 'fitch_rating_1', 325)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM rating WHERE id = 10", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void UpdateRatingExistingFailedShouldReturnRatingUpdatePage() throws Exception {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setMoodysRating("MoodysRatingUpdatedMoreThan20Letters");
        ratingDTO.setSandPRating("SandUpdated");
        ratingDTO.setFitchRating("FitchUpdated");
        ratingDTO.setOrderNumber(125);
        ratingDTO.setRatingID(10);

        mockMvc.perform(post("/api/rating/update/{id}", 10)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("moodysRating", ratingDTO.getMoodysRating())
                        .param("sandPRating", ratingDTO.getSandPRating())
                        .param("fitchRating", ratingDTO.getFitchRating())
                        .param("orderNumber", String.valueOf(ratingDTO.getOrderNumber()))
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));

        assertThat(ratingTestH2Repository.findById(10).get().getFitchRating()).isEqualTo("fitch_rating_1");
    }
    @Test
    @WithMockUser("UserTest")
    @Sql(statements = "INSERT INTO rating (id, moodys_rating, sand_p_rating, fitch_rating, order_number) VALUES (8, 'moodys_rating_1', 'sandPRating1', 'fitch_rating_1', 325)", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM rating WHERE id = 8", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteRatingExistingShouldReturnRatingListPage() throws Exception {
        mockMvc.perform(get("/api/rating/delete/{id}", 8))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/rating/list"));

        assertAll(
                () -> assertThat(ratingTestH2Repository.findById(8)).isEmpty(),
                () -> assertThat(ratingTestH2Repository.findAll().size()).isEqualTo(0)
        );
    }

}
