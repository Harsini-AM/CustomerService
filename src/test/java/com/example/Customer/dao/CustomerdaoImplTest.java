package com.example.Customer.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Customer.entity.Ticket;
import com.example.Customer.repo.TicketRepo;

@ExtendWith(MockitoExtension.class)
class CustomerdaoImplTest {

    @InjectMocks
    private CustomerdaoImpl customerdao;

    @Mock
    private TicketRepo ticketRepo;

    @BeforeEach
    void setUp() {
        
    }

    @Test
    void testGetTicketsByManagerId() {
        
        when(ticketRepo.findByManagerId(26L)).thenReturn(Arrays.asList(new Ticket()));

        
        List<Ticket> tickets = customerdao.getTicketsByManagerId(26L);

        
        assertEquals(1, tickets.size());

        verify(ticketRepo, times(1)).findByManagerId(26L);
    }

    @Test
    void testGetTicketsByManagerIdWhenNotPresent() {
        
        when(ticketRepo.findByManagerId(29L)).thenReturn(Collections.emptyList());

        
        List<Ticket> tickets = customerdao.getTicketsByManagerId(29L);

        
        assertEquals(0, tickets.size());

       
        verify(ticketRepo, times(1)).findByManagerId(29L);
    }
    
    @Test
    void testCalculateTop5RepWiseAverageResponseTime() {
        // Set up mock behavior
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        tickets.get(0).setEmpId(1L);
        tickets.get(0).setResponseTime((float) 10.0);
        tickets.get(1).setEmpId(2L);
        tickets.get(1).setResponseTime((float) 20.0);
        when(ticketRepo.findByManagerId(26L)).thenReturn(tickets);

        // Call the method under test
        Map<Long, Double> top5ResponseTimes = customerdao.calculateTop5RepWiseAverageResponseTime(26L);

        // Assert the results
        assertEquals(2, top5ResponseTimes.size());

        // Verify interactions
        verify(ticketRepo, times(1)).findByManagerId(26L);
    }

    @Test
    void testCalculateTop5RepWiseAverageResolutionTime() {
        // Set up mock behavior
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        tickets.get(0).setEmpId(1L);
        tickets.get(0).setResolutionTime((float) 30.0);
        tickets.get(1).setEmpId(2L);
        tickets.get(1).setResolutionTime((float) 40.0);
        when(ticketRepo.findByManagerId(26L)).thenReturn(tickets);

        // Call the method under test
        Map<Long, Double> top5ResolutionTimes = customerdao.calculateTop5RepWiseAverageResolutionTime(26L);

        // Assert the results
        assertEquals(2, top5ResolutionTimes.size());

        // Verify interactions
        verify(ticketRepo, times(1)).findByManagerId(26L);
    }

    @Test
    void testGetTicketCountsByStatus() {
        // Set up mock behavior
        List<Object[]> results = Arrays.asList(new Object[]{"OPEN", 1L}, new Object[]{"CLOSED", 2L});
        when(ticketRepo.countTicketsByStatus(26L)).thenReturn(results);

        // Call the method under test
        Map<String, Long> ticketCounts = customerdao.getTicketCountsByStatus(26L);

        // Assert the results
        assertEquals(2, ticketCounts.size());
        assertEquals(1L, ticketCounts.get("OPEN"));
        assertEquals(2L, ticketCounts.get("CLOSED"));

        // Verify interactions
        verify(ticketRepo, times(1)).countTicketsByStatus(26L);
    }

    @Test
    void testGetAverageResponseTimeByRepId() {
        // Set up mock behavior
        double expectedResponseTime = 20.0;
        when(ticketRepo.findAverageResponseTimeByRepId(18L)).thenReturn(Optional.of(expectedResponseTime));

        // Call the method under test
        double averageResponseTime = customerdao.getAverageResponseTimeByRepId(18L);

        // Assert the results
        assertEquals(expectedResponseTime, averageResponseTime);

        // Verify interactions
        verify(ticketRepo, times(1)).findAverageResponseTimeByRepId(18L);
    }

    @Test
    void testGetAverageResolutionTimeByRepId() {
        // Set up mock behavior
        double expectedResolutionTime = 80.0;
        when(ticketRepo.findAverageResolutionTimeByRepId(18L)).thenReturn(Optional.of(expectedResolutionTime));

        // Call the method under test
        double averageResolutionTime = customerdao.getAverageResolutionTimeByRepId(18L);

        // Assert the results
        assertEquals(expectedResolutionTime, averageResolutionTime);

        // Verify interactions
        verify(ticketRepo, times(1)).findAverageResolutionTimeByRepId(18L);
    }

    @Test
    void testGetTicketCountsByStatusForRep() {
        // Set up mock behavior
        List<Object[]> results = Arrays.asList(new Object[]{"OPEN", 1L}, new Object[]{"CLOSED", 2L});
        when(ticketRepo.countTicketsByStatusForRep(28L)).thenReturn(results);

        // Call the method under test
        Map<String, Long> ticketCounts = customerdao.getTicketCountsByStatusForRep(28L);

        // Assert the results
        assertEquals(2, ticketCounts.size());
        assertEquals(1L, ticketCounts.get("OPEN"));
        assertEquals(2L, ticketCounts.get("CLOSED"));

        // Verify interactions
        verify(ticketRepo, times(1)).countTicketsByStatusForRep(28L);
    }

    @Test
    void testGetAverageResponseTimeByDayOfWeek() {
        // Set up mock behavior
        LocalDate currentDate = LocalDate.now();
        List<Object[]> results = Arrays.asList(
            new Object[]{currentDate.atStartOfDay().minusDays(1), 10.0},
            new Object[]{currentDate.atStartOfDay().minusDays(2), 20.0}
        );
        when(ticketRepo.findResponseTimesForRep(28L, currentDate.atStartOfDay().minusDays(7))).thenReturn(results);

        // Call the method under test
        Map<String, Float> avgResponseTimes = customerdao.getAverageResponseTimeByDayOfWeek(28L, currentDate);

        // Assert the results
        assertEquals(2, avgResponseTimes.size());
        assertEquals(10.0f, avgResponseTimes.get(currentDate.minusDays(1).getDayOfWeek().name()));
        assertEquals(20.0f, avgResponseTimes.get(currentDate.minusDays(2).getDayOfWeek().name()));

        // Verify interactions
        verify(ticketRepo, times(1)).findResponseTimesForRep(28L, currentDate.atStartOfDay().minusDays(7));
    }

    @Test
    void testGetAverageResolutionTimeByDayOfWeek() {
        // Set up mock behavior
        LocalDate currentDate = LocalDate.now();
        List<Object[]> results = Arrays.asList(
            new Object[]{currentDate.atStartOfDay().minusDays(1), 30.0},
            new Object[]{currentDate.atStartOfDay().minusDays(2), 40.0}
        );
        when(ticketRepo.findResolutionTimesForRep(28L, currentDate.atStartOfDay().minusDays(7))).thenReturn(results);

        // Call the method under test
        Map<String, Float> avgResolutionTimes = customerdao.getAverageResolutionTimeByDayOfWeek(28L, currentDate);

        // Assert the results
        assertEquals(2, avgResolutionTimes.size());
        assertEquals(30.0f, avgResolutionTimes.get(currentDate.minusDays(1).getDayOfWeek().name()));
        assertEquals(40.0f, avgResolutionTimes.get(currentDate.minusDays(2).getDayOfWeek().name()));

        // Verify interactions
        verify(ticketRepo, times(1)).findResolutionTimesForRep(28L, currentDate.atStartOfDay().minusDays(7));
    }

    @Test
    void testGetAverageResponseTimeByDayOfWeekIfRepNotFound() {
        // Set up mock behavior
        LocalDate currentDate = LocalDate.now();
        when(ticketRepo.findResponseTimesForRep(1L, currentDate.atStartOfDay().minusDays(7))).thenReturn(Collections.emptyList());

        // Call the method under test and assert exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            customerdao.getAverageResponseTimeByDayOfWeek(1L, currentDate);
        });

        assertEquals("Error getting average response time by day of week for representative", exception.getMessage());

        // Verify interactions
        verify(ticketRepo, times(1)).findResponseTimesForRep(1L, currentDate.atStartOfDay().minusDays(7));
    }

    @Test
    void testGetAverageResolutionTimeByDayOfWeekIfRepNotFound() {
    	LocalDate currentDate = LocalDate.now();
        Map<String,Float> result = new HashMap<String,Float>();
        LocalDate currentDate1 = LocalDate.now();
        Map<String, Float> avgResolutionTimes = customerdao.getAverageResolutionTimeByDayOfWeek(1L, currentDate1);
        assertEquals(result, avgResolutionTimes);
    }
    
    @Test
    public void testGetTicketsByManagerId_Exception() {
        when(ticketRepo.findByManagerId(12L)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
        	customerdao.getTicketsByManagerId(12L);
        });

        assertEquals("Error fetching tickets by manager ID", exception.getMessage());
    }

    
    @Test
    public void testGetTicketCountsByStatus_Exception() {
        when(ticketRepo.countTicketsByStatus(10L)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerdao.getTicketCountsByStatus(10L);
        });

        assertEquals("Error getting ticket counts by status", exception.getMessage());
    }
    
    @Test
    public void testGetAverageResponseTimeByRepId_Exception() {
        when(ticketRepo.findAverageResponseTimeByRepId(20L)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerdao.getAverageResponseTimeByRepId(20L);
        });

        assertEquals("Error getting average response time by representative ID", exception.getMessage());
    }
    
    @Test
    public void testGetAverageResolutionTimeByRepId_Exception() {
        when(ticketRepo.findAverageResolutionTimeByRepId(20L)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerdao.getAverageResolutionTimeByRepId(20L);
        });

        assertEquals("Error getting average resolution time by representative ID", exception.getMessage());
    }
    
    
    @Test
    public void testGetTicketCountsByStatusForRep_Exception() {
        when(ticketRepo.countTicketsByStatusForRep(20L)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerdao.getTicketCountsByStatusForRep(20L);
        });

        assertEquals("Error getting ticket counts by status for representative: Database error", exception.getMessage());
    }
    
    @Test
    public void testGetAverageResolutionTimeByDayOfWeek_Exception() {
        LocalDate currentDate = LocalDate.now();
        when(ticketRepo.findResolutionTimesForRep(10L, currentDate.atStartOfDay().minusDays(7)))
            .thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerdao.getAverageResolutionTimeByDayOfWeek(10L, currentDate);
        });

        assertEquals("Error getting average resolution time by day of week for representative", exception.getMessage());
    }

}
