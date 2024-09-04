package com.example.Customer.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class TicketTests {

    @Test
    void testGetterAndSetter() {
        // Create a ticket instance
        Ticket ticket = new Ticket();

        // Set properties using setter methods
        long ticketId = 1L;
        String domain = "Example Domain";
        String description = "Example Description";
        long empId = 12345L;
        long managerId = 67890L;
        String status = "Example Status";
        LocalDateTime createdAt = LocalDateTime.now();
        float responseTime = 2.5f;
        float resolutionTime = 5.0f;

        ticket.setTicketId(ticketId);
        ticket.setDomain(domain);
        ticket.setDescription(description);
        ticket.setEmpId(empId);
        ticket.setManagerId(managerId);
        ticket.setStatus(status);
        ticket.setCreatedAt(createdAt);
        ticket.setResponseTime(responseTime);
        ticket.setResolutionTime(resolutionTime);

        // Verify getter methods
        assertEquals(ticketId, ticket.getTicketId());
        assertEquals(domain, ticket.getDomain());
        assertEquals(description, ticket.getDescription());
        assertEquals(empId, ticket.getEmpId());
        assertEquals(managerId, ticket.getManagerId());
        assertEquals(status, ticket.getStatus());
        assertEquals(createdAt, ticket.getCreatedAt());
        assertEquals(responseTime, ticket.getResponseTime());
        assertEquals(resolutionTime, ticket.getResolutionTime());
    }

    @Test
    void testToString() {
        // Create a ticket instance
        Ticket ticket = new Ticket();
        ticket.setTicketId(1L);
        ticket.setDomain("Example Domain");
        ticket.setDescription("Example Description");
        ticket.setEmpId(12345L);
        ticket.setManagerId(67890L);
        ticket.setStatus("Example Status");
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setResponseTime(2.5f);
        ticket.setResolutionTime(5.0f);

        // Verify toString() method
        String expectedToString = "Ticket [TicketId=1, domain=Example Domain, description=Example Description, empId=12345, managerId=67890, status=Example Status, createdAt=" + ticket.getCreatedAt() + ", responseTime=2.5, resolutionTime=5.0, customer=null]";
        assertEquals(expectedToString, ticket.toString());
    }

    @Test
    void testNoArgsConstructor() {
        // Test no-args constructor
        Ticket ticket = new Ticket();
        assertNotNull(ticket);
    }

    
}
