package com.example.Customer.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Customer {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private long id;
	    
	@Column(unique = true, nullable = false)
	private String userName;
	
    private String password;
    private String fName;
    private String lName;
    private String city;
    private String state;
    private Long phone_no;
    private String planType;
    private String planName;
    private String planDescription;
    
    
    @JsonManagedReference
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    // Other getters and setters

  
   
	
	
}
