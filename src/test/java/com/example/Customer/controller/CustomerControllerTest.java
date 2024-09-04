package com.example.Customer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.Customer.dao.CustomerdaoImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerdaoImpl customerService;

    @InjectMocks
    private CustomerController customerController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void testGetResponseAverageByManagerId() throws Exception {
        Map<Long, Double> result = new HashMap<>();
        result.put(14L, 30.0);
        Long managerId = 26L;

        when(customerService.calculateTop5RepWiseAverageResponseTime(anyLong())).thenReturn(result);

        mockMvc.perform(get("/customer/responseAverage/"+managerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    void testGetResolutionAverageByManagerId() throws Exception {
        Map<Long, Double> result = new HashMap<>();
        result.put(14L, 90.0);
        Long managerId = 26L;

        when(customerService.calculateTop5RepWiseAverageResolutionTime(anyLong())).thenReturn(result);

        mockMvc.perform(get("/customer/resolutionAverage/"+managerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    void testGetTicketCountsByStatus() throws Exception {
        Map<String, Long> result = new HashMap<>();
        result.put("OPEN", 1L);
        Long managerId = 26L;

        when(customerService.getTicketCountsByStatus(anyLong())).thenReturn(result);

        mockMvc.perform(get("/customer/statusCounts/"+managerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    void testGetAverageResponseTimeByRepId() throws Exception {
        double result = 50.0;
        Long repId = 26L;

        when(customerService.getAverageResponseTimeByRepId(anyLong())).thenReturn(result);

        mockMvc.perform(get("/customer/averageResponseTime/"+repId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    void testGetAverageResolutionTimeByRepId() throws Exception {
        double result = 20.0;
        Long repId = 26L;

        when(customerService.getAverageResolutionTimeByRepId(anyLong())).thenReturn(result);

        mockMvc.perform(get("/customer/averageResolutionTime/"+repId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    void testGetTicketCountsByStatusForRep() throws Exception {
        Map<String, Long> result = new HashMap<>();
        result.put("OPEN", 1L);
        Long repId = 26L;

        when(customerService.getTicketCountsByStatusForRep(anyLong())).thenReturn(result);

        mockMvc.perform(get("/customer/statusCountsForRep/"+repId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    void testGetAverageResponseTime() throws Exception {
        Map<String, Float> result = new HashMap<>();
        result.put("FRIDAY", 50.0f);
        Long repId = 28L;

        LocalDate date = LocalDate.now();
        when(customerService.getAverageResponseTimeByDayOfWeek(anyLong(), any(LocalDate.class))).thenReturn(result);

        mockMvc.perform(get("/customer/weeklyResponseTime/"+repId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    void testGetAverageResolutionTime() throws Exception {
        Map<String, Float> result = new HashMap<>();
        result.put("FRIDAY", 20.0f);
        Long repId = 28L;

        LocalDate date = LocalDate.now();
        when(customerService.getAverageResolutionTimeByDayOfWeek(anyLong(), any(LocalDate.class))).thenReturn(result);

        mockMvc.perform(get("/customer/weeklyResolutionTime/"+repId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }
}

