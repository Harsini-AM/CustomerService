package com.example.Customer.controller;

import java.time.LocalDate;	
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Customer.dao.CustomerdaoImpl;
import com.example.Customer.entity.Ticket;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerdaoImpl customerService;

   

    
    
    @GetMapping("/responseAverage/{managerId}")
    public ResponseEntity<?> getResponseAverageByManagerId(@PathVariable Long managerId){
    	Map<Long, Double> result = customerService.calculateTop5RepWiseAverageResponseTime(managerId);
    	System.out.println("result is "+result);
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    
  
    @GetMapping("/resolutionAverage/{managerId}")
    public ResponseEntity<?> getResolutionAverageByManagerId(@PathVariable Long managerId){
    	Map<Long, Double> result = customerService.calculateTop5RepWiseAverageResolutionTime(managerId);
    	return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/statusCounts/{managerId}")
    public ResponseEntity<Map<String, Long>> getTicketCountsByStatus(@PathVariable Long managerId) {
        Map<String, Long> ticketCounts = customerService.getTicketCountsByStatus(managerId);
        return ResponseEntity.ok(ticketCounts);
    }
    
    
    @GetMapping("/averageResponseTime/{repId}")
    public ResponseEntity<Double> getAverageResponseTimeByRepId(@PathVariable Long repId) {
        double averageResponseTime = customerService.getAverageResponseTimeByRepId(repId);
        return ResponseEntity.ok(averageResponseTime);
    }
 
    @GetMapping("/averageResolutionTime/{repId}")
    public ResponseEntity<Double> getAverageResolutionTimeByRepId(@PathVariable Long repId) {
        double averageResolutionTime = customerService.getAverageResolutionTimeByRepId(repId);
        return ResponseEntity.ok(averageResolutionTime);
    }
    
    
    @GetMapping("/statusCountsForRep/{repId}")
    public ResponseEntity<?> getTicketCountsByStatusForRep(@PathVariable Long repId) {
       
            Map<String, Long> ticketCounts = customerService.getTicketCountsByStatusForRep(repId);
            return ResponseEntity.ok(ticketCounts);
        
    }
    
    @GetMapping("/weeklyResponseTime/{repId}")
    public Map<String, Float> getAverageResponseTime(@PathVariable Long repId) {
        LocalDate date = LocalDate.now();
        return customerService.getAverageResponseTimeByDayOfWeek(repId, date);
    }
    
    
    @GetMapping("/weeklyResolutionTime/{repId}")
    public Map<String, Float> getAverageResolutionTime(@PathVariable Long repId) {
        LocalDate date = LocalDate.now();
        return customerService.getAverageResolutionTimeByDayOfWeek(repId, date);
    }


   
}