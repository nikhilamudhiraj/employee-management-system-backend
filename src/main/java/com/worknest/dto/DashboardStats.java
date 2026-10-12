package com.worknest.dto;

import java.util.List;
import java.util.Map;

public class DashboardStats {
    private long totalEmployees;
    private long activeEmployees;
    private long onLeaveEmployees;
    private Double totalPayroll;
    private Double averageSalary;
    private List<Map<String, Object>> departmentBreakdown;

    public DashboardStats() {
    }

    public DashboardStats(long totalEmployees, long activeEmployees, long onLeaveEmployees, 
                          Double totalPayroll, Double averageSalary, 
                          List<Map<String, Object>> departmentBreakdown) {
        this.totalEmployees = totalEmployees;
        this.activeEmployees = activeEmployees;
        this.onLeaveEmployees = onLeaveEmployees;
        this.totalPayroll = totalPayroll;
        this.averageSalary = averageSalary;
        this.departmentBreakdown = departmentBreakdown;
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public long getActiveEmployees() {
        return activeEmployees;
    }

    public void setActiveEmployees(long activeEmployees) {
        this.activeEmployees = activeEmployees;
    }

    public long getOnLeaveEmployees() {
        return onLeaveEmployees;
    }

    public void setOnLeaveEmployees(long onLeaveEmployees) {
        this.onLeaveEmployees = onLeaveEmployees;
    }

    public Double getTotalPayroll() {
        return totalPayroll;
    }

    public void setTotalPayroll(Double totalPayroll) {
        this.totalPayroll = totalPayroll;
    }

    public Double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(Double averageSalary) {
        this.averageSalary = averageSalary;
    }

    public List<Map<String, Object>> getDepartmentBreakdown() {
        return departmentBreakdown;
    }

    public void setDepartmentBreakdown(List<Map<String, Object>> departmentBreakdown) {
        this.departmentBreakdown = departmentBreakdown;
    }
}
