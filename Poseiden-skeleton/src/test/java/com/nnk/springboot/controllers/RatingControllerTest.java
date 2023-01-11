package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.service.rating_service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RatingController.class)
class RatingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private RatingService ratingService;

    private List<RatingDTO> ratingList;
    @BeforeEach
    void init() {
        RatingDTO rating1 = new RatingDTO();
        rating1.setSandPRating("sandPRating1");
        rating1.setFitchRating("fitchRating1");
        rating1.setMoodysRating("moodysRating1");
        rating1.setOrderNumber(36);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        ratingList = new ArrayList<>(List.of(rating1));
    }
    @Test
    @WithMockUser
    void home() throws Exception {
        when(ratingService.getRatings()).thenReturn(ratingList);

        mockMvc.perform(get("/api/rating/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("ratingList", ratingList))
                .andExpect(view().name("rating/list"));
    }

    @Test
    @WithMockUser
    void addRatingForm() throws Exception {
        mockMvc.perform(get("/api/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser
    void validateFailed() throws Exception {
        mockMvc.perform(post("/api/rating/validate"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }
    @Test
    void validate() throws Exception {
        RatingDTO rating = new RatingDTO();
        rating.setSandPRating("new_sandPRating");
        rating.setFitchRating("new_fitchRating");
        rating.setMoodysRating("new_moodysRating");
        rating.setOrderNumber(45);

        when(ratingService.saveNewRating(rating)).thenReturn(rating);

        mockMvc.perform(post("/api/rating/validate")
                .param("moodysRating", "new_moodysRating")
                .param("sandPRating", "new_sandPRating")
                .param("fitchRating", "new-fitchRating")
                .param("orderNumber", String.valueOf(45)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/rating/list"));
    }

    @Test
    @WithMockUser
    void showUpdateForm() throws Exception {
        RatingDTO rating = new RatingDTO();
        rating.setSandPRating("new_sandPRating");
        rating.setFitchRating("new_fitchRating");
        rating.setMoodysRating("new_moodysRating");
        rating.setOrderNumber(78);

        when(ratingService.getRatingById(anyInt())).thenReturn(rating);

        mockMvc.perform(get("/api/rating/update/{id}", 6))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    @WithMockUser
    void updateRatingFailed() throws Exception {
        mockMvc.perform(post("/api/rating/update/{id}", 5))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    @WithMockUser
    void updateRating() throws Exception {
        RatingDTO rating = new RatingDTO();
        rating.setSandPRating("update_sandPRating");
        rating.setFitchRating("update_fitchRating");
        rating.setMoodysRating("update_moodysRating");
        rating.setOrderNumber(87);
        rating.setRatingID(9);

        doNothing().when(ratingService).updateRating(rating);

        mockMvc.perform(post("/api/rating/update/{id}", 9)
                .param("moodysRating", "update_moodysRating")
                .param("sandPRating", "update_sandPRating")
                .param("fitchRating", "update-fitchRating")
                .param("orderNumber", String.valueOf(87)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/rating/list"));
    }

    @Test
    @WithMockUser
    void deleteRating() throws Exception {
        doNothing().when(ratingService).deleteRatingById(3);

        mockMvc.perform(get("/api/rating/delete/{id}", 3))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/rating/list"));

    }
}