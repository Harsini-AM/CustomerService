package com.example.Customer.repo;

import com.example.Customer.entity.Ticket;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TicketRepo extends JpaRepository<Ticket, Long> {
	List<Ticket> findAllByCustomerId(long customerId);
	

    
    
    //harsini
    
    Optional<Ticket> findById(Long id);
    
	List<Ticket> findByManagerId(Long managerId);
	
	List<Ticket> findByEmpId(Long id);
	
	
	@Query("SELECT t.status, COUNT(t) FROM Ticket t WHERE t.managerId = :managerId GROUP BY t.status")
    List<Object[]> countTicketsByStatus(Long managerId);
    
    @Query("SELECT t.status, COUNT(t) FROM Ticket t WHERE t.empId = :repId GROUP BY t.status")
    List<Object[]> countTicketsByStatusForRep(Long repId);
	
	
	 @Query("SELECT AVG(t.responseTime) FROM Ticket t WHERE t.empId = :repId")
	 Optional<Double> findAverageResponseTimeByRepId(Long repId);
 
	 @Query("SELECT AVG(t.resolutionTime) FROM Ticket t WHERE t.empId = :repId")
	 Optional<Double> findAverageResolutionTimeByRepId(Long repId);
	
	
	 @Query("SELECT t.createdAt, t.responseTime FROM Ticket t WHERE t.empId = :repId AND t.createdAt >= :startDate")
	  List<Object[]> findResponseTimesForRep(Long repId, LocalDateTime startDate);
	  
	  @Query("SELECT t.createdAt, t.resolutionTime FROM Ticket t WHERE t.empId = :repId AND t.createdAt >= :startDate")
	  List<Object[]> findResolutionTimesForRep(Long repId, LocalDateTime startDate);
	  
	  
}
