// EmployeeResponse.java
package com.example.Customer.dto;

public class EmployeeResponse {
    private long empId;
    private long managerId;

    // getters and setters
    public long getEmpId() {
        return empId;
    }

    public void setEmpId(long empId) {
        this.empId = empId;
    }

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }
}

