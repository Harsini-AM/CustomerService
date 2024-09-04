package com.example.Customer.dao;

import java.time.LocalDate;	
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.Customer.dto.EmployeeResponse;
import com.example.Customer.entity.Ticket;

import com.example.Customer.repo.TicketRepo;

@Service
public class CustomerdaoImpl{

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private TicketRepo ticketRepo;





    
    //harsini  
    public List<Ticket> getTicketsByManagerId(Long managerId) {
        try {
            return ticketRepo.findByManagerId(managerId);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching tickets by manager ID", e);
        }
    }

 
    public double getAverageResolutionTimeByManagerId(Long managerId) {
        try {
            List<Ticket> tickets = getTicketsByManagerId(managerId);
            return tickets.stream()
                          .mapToDouble(Ticket::getResolutionTime)
                          .average()
                          .orElse(0.0);
        } catch (Exception e) {
            throw new RuntimeException("Error getting average resolution time by manager ID", e);
        }
    }

    public Map<Long, Double> calculateTop5RepWiseAverageResponseTime(Long managerId) {
        try {
            List<Ticket> tickets = getTicketsByManagerId(managerId);
            Map<Long, Double> repAvgResponseTime = tickets.stream()
                    .collect(Collectors.groupingBy(Ticket::getEmpId, Collectors.averagingDouble(Ticket::getResponseTime)));
            Map<Long,Double> sorted =  repAvgResponseTime.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .limit(5)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                    ));
            return sorted;
        } catch (Exception e) {
            throw new RuntimeException("Error calculating top 5 representative-wise average response time", e);
        }
    }


		
    public Map<Long,Double> calculateTop5RepWiseAverageResolutionTime(Long managerId){
        try {
            List<Ticket> tickets = getTicketsByManagerId(managerId);
            
            // Calculate average resolution time for each representative
            Map<Long, Double> repAvgResolutionTime = tickets.stream()
                    .collect(Collectors.groupingBy(Ticket::getEmpId, Collectors.averagingDouble(Ticket::getResolutionTime)));
            
            // Sort the representatives by their average resolution time and take the top 5
            Map<Long,Double> sorted =  repAvgResolutionTime.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .limit(5)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                    ));
            
            System.out.println(sorted);
            return sorted;
        } catch (Exception e) {
            // Handle exception or rethrow
            throw new RuntimeException("Error calculating top 5 representative-wise average resolution time", e);
        }
    }

    public Map<String, Long> getTicketCountsByStatus(Long managerId) {
        try {
           // List<Object[]> results = ticketRepo.countTicketsByStatus(managerId);
            Map<String, Long> ticketCounts = new HashMap<>();

            for (Object[] result : results) {
                String status = (String) result[0];
                Long count = (Long) result[1];
                ticketCounts.put(status, count);
            }
            System.out.println(ticketCounts.size());

            return ticketCounts;
        } catch (Exception e) {
            throw new RuntimeException("Error getting ticket counts by status", e);
        }
    }

    public double getAverageResponseTimeByRepId(Long repId) {
        try {
            Optional<Double> result = ticketRepo.findAverageResponseTimeByRepId(repId);
            return result.orElse(0.0);
        } catch (Exception e) {
            throw new RuntimeException("Error getting average response time by representative ID", e);
        }
    }

    public double getAverageResolutionTimeByRepId(Long repId) {
        try {
            Optional<Double> result = ticketRepo.findAverageResolutionTimeByRepId(repId);
            return result.orElse(0.0);
        } catch (Exception e) {
            throw new RuntimeException("Error getting average resolution time by representative ID", e);
        }
    }


		
		
		
		
    public Map<String, Long> getTicketCountsByStatusForRep(Long repId) {
        try {
            List<Object[]> results = ticketRepo.countTicketsByStatusForRep(repId);
            Map<String, Long> ticketCounts = new HashMap<>();

            for (Object[] result : results) {
                String status = (String) result[0];
                Long count = (Long) result[1];
                ticketCounts.put(status, count);
            }

            return ticketCounts;
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            throw new RuntimeException("Error getting ticket counts by status for representative: " + e.getMessage(), e);
        }
    }

		public Map<String, Float> getAverageResponseTimeByDayOfWeek(Long repId, LocalDate currentDate) {
		    try {
		        LocalDateTime startDate = currentDate.atStartOfDay().minusDays(7);
		        List<Object[]> results = ticketRepo.findResponseTimesForRep(repId, startDate);
		        
		        if(results.size()==0) {
		        	throw new Exception("No representative found");
		        }
		        // Collect response times by day
		        Map<LocalDate, List<Float>> responseTimesByDay = new HashMap<>();
		        for (Object[] result : results) {
		            LocalDate createdAt = ((LocalDateTime) result[0]).toLocalDate();
		            float responseTime = ((Number) result[1]).floatValue();

		            responseTimesByDay
		                .computeIfAbsent(createdAt, k -> new ArrayList<>())
		                .add(responseTime);
		        }
		        System.out.println(responseTimesByDay);

		        // Calculate average response times and store in a sorted map
		        Map<LocalDate, Float> avgResponseTimesByDate = responseTimesByDay.entrySet().stream()
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                entry -> (float) entry.getValue().stream().mapToDouble(Float::floatValue).average().orElse(0.0)
		            ));

		        // Sort the map by LocalDate
		        Map<LocalDate, Float> sortedAvgResponseTimesByDate = avgResponseTimesByDate.entrySet().stream()
		            .sorted(Map.Entry.comparingByKey())
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (e1, e2) -> e1,
		                LinkedHashMap::new
		            ));

		        // Convert sorted map to day-of-week format
		        Map<String, Float> avgResponseTimesByDayOfWeek = new LinkedHashMap<>();
		        for (Map.Entry<LocalDate, Float> entry : sortedAvgResponseTimesByDate.entrySet()) {
		            avgResponseTimesByDayOfWeek.put(entry.getKey().getDayOfWeek().name(), entry.getValue());
		        }

		        return avgResponseTimesByDayOfWeek;
		    } catch (Exception e) {
		        throw new RuntimeException("Error getting average response time by day of week for representative", e);
		    }
		}

		public Map<String, Float> getAverageResolutionTimeByDayOfWeek(Long repId, LocalDate currentDate) {
		    try {
		        LocalDateTime startDate = currentDate.atStartOfDay().minusDays(7);
		        List<Object[]> results = ticketRepo.findResolutionTimesForRep(repId, startDate);
		        
		        
		        
		        // Collect resolution times by day
		        Map<LocalDate, List<Float>> resolutionTimesByDay = new HashMap<>();
		        for (Object[] result : results) {
		            LocalDate createdAt = ((LocalDateTime) result[0]).toLocalDate();
		            float resolutionTime = ((Number) result[1]).floatValue();

		            resolutionTimesByDay
		                .computeIfAbsent(createdAt, k -> new ArrayList<>())
		                .add(resolutionTime);
		        }
		        System.out.println(resolutionTimesByDay);

		        // Calculate average resolution times
		        Map<LocalDate, Float> avgResolutionTimesByDate = resolutionTimesByDay.entrySet().stream()
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                entry -> (float) entry.getValue().stream().mapToDouble(Float::floatValue).average().orElse(0.0)
		            ));

		        // Sort the map by LocalDate
		        Map<LocalDate, Float> sortedAvgResolutionTimesByDate = avgResolutionTimesByDate.entrySet().stream()
		            .sorted(Map.Entry.comparingByKey())
		            .collect(Collectors.toMap(
		                Map.Entry::getKey,
		                Map.Entry::getValue,
		                (e1, e2) -> e1,
		                LinkedHashMap::new
		            ));

		        // Convert sorted map to day-of-week format
		        Map<String, Float> avgResolutionTimesByDayOfWeek = new LinkedHashMap<>();
		        for (Map.Entry<LocalDate, Float> entry : sortedAvgResolutionTimesByDate.entrySet()) {
		            avgResolutionTimesByDayOfWeek.put(entry.getKey().getDayOfWeek().name(), entry.getValue());
		        }

		        return avgResolutionTimesByDayOfWeek;
		    } catch (Exception e) {
		        throw new RuntimeException("Error getting average resolution time by day of week for representative", e);
		    }
		}


		

		
	
}




