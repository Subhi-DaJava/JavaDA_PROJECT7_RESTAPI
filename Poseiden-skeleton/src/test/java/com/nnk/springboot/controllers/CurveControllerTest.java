package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.service.curve_point_service.CurvePointService;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CurveController.class)
class CurveControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CurvePointService curvePointService;

    private List<CurvePointDTO> curvePointList;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        curvePointList = new ArrayList<>(
                List.of(new CurvePointDTO(3, 201D, 500D),
                        new CurvePointDTO(20, 404D, 263D)));
    }
    @Test
    @WithMockUser
    void home() throws Exception {
        when(curvePointService.getCurvePoints()).thenReturn(curvePointList);

        mockMvc.perform(get("/api/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("curvePointList", curvePointList))
                .andExpect(view().name("curvePoint/list"));
    }

    @Test
    @WithMockUser
    void addCurveForm() throws Exception {
        mockMvc.perform(get("/api/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser
    void validate() throws Exception {
        CurvePointDTO curvePointDTO = new CurvePointDTO(33, 204D, 785D);

        when(curvePointService.saveNewCurvePoint(curvePointDTO)).thenReturn(curvePointDTO);

        mockMvc.perform(post("/api/curvePoint/validate")
                .param("curveId", String.valueOf(33))
                .param("term", String.valueOf(204D))
                .param("value", String.valueOf(785D))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/curvePoint/list"));
    }
    @Test
    @WithMockUser
    void validateFailed() throws Exception {
        mockMvc.perform(post("/api/curvePoint/validate"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser
    void showUpdateForm() throws Exception {
        CurvePointDTO curvePointDTO = new CurvePointDTO(33, 204D, 785D);
        curvePointDTO.setCurveid(4);
        when(curvePointService.getCurvePointById(anyInt())).thenReturn(curvePointDTO);

        mockMvc.perform(get("/api/curvePoint/update/{id}", 4))
                .andExpect(status().isOk())
                .andExpect(model().attribute("curvePointDTO", curvePointDTO))
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @WithMockUser
    void updateCurvePoint() throws Exception {
        CurvePointDTO curvePointDTO = new CurvePointDTO(45, 240D, 652D);
        curvePointDTO.setCurveid(4);
        doNothing().when(curvePointService).updateCurvePoint(curvePointDTO);

        mockMvc.perform(post("/api/curvePoint/update/{id}", 4)
                .param("curveId", String.valueOf(45))
                .param("term", String.valueOf(240D))
                .param("value", String.valueOf(652D))
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/curvePoint/list"));


    }
    @Test
    @WithMockUser
    void updateCurvePointFailed() throws Exception {
       mockMvc.perform(post("/api/curvePoint/update/{id}", 5))
               .andExpect(status().isOk())
               .andExpect(view().name("curvePoint/update"));
    }

    @Test
    void deleteCurvePoint() throws Exception {
        doNothing().when(curvePointService).deleteCurvePointById(4);

        mockMvc.perform(get("/api/curvePoint/delete/{id}", 4))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/curvePoint/list"));


    }
}