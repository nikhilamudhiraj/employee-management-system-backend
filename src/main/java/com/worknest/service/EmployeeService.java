package com.worknest.service;

import com.worknest.dto.DashboardStats;
import com.worknest.model.Employee;
import com.worknest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public List<Employee> getEmployees(String query, String department, String status) {
        if ((query == null || query.isBlank()) &&
            (department == null || department.isBlank() || department.equalsIgnoreCase("All")) &&
            (status == null || status.isBlank() || status.equalsIgnoreCase("All"))) {
            return employeeRepository.findAll();
        }

        String filterDept = (department != null && !department.equalsIgnoreCase("All") && !department.isBlank()) ? department : null;
        String filterStat = (status != null && !status.equalsIgnoreCase("All") && !status.isBlank()) ? status : null;
        String filterQuery = (query != null && !query.isBlank()) ? query.trim() : null;

        return employeeRepository.searchAndFilter(filterQuery, filterDept, filterStat);
    }

    @Transactional(readOnly = true)
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("An employee with email '" + employee.getEmail() + "' already exists.");
        }
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee updatedData) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));

        if (employeeRepository.existsByEmailAndIdNot(updatedData.getEmail(), id)) {
            throw new IllegalArgumentException("Email '" + updatedData.getEmail() + "' is already in use by another employee.");
        }

        existing.setFirstName(updatedData.getFirstName());
        existing.setLastName(updatedData.getLastName());
        existing.setEmail(updatedData.getEmail());
        existing.setPhone(updatedData.getPhone());
        existing.setDepartment(updatedData.getDepartment());
        existing.setDesignation(updatedData.getDesignation());
        existing.setSalary(updatedData.getSalary());
        if (updatedData.getStatus() != null) {
            existing.setStatus(updatedData.getStatus());
        }
        if (updatedData.getJoiningDate() != null) {
            existing.setJoiningDate(updatedData.getJoiningDate());
        }
        existing.setLocation(updatedData.getLocation());
        existing.setAvatarUrl(updatedData.getAvatarUrl());

        return employeeRepository.save(existing);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public DashboardStats getDashboardStats() {
        long total = employeeRepository.count();
        long active = employeeRepository.countActiveEmployees();
        long onLeave = employeeRepository.countOnLeaveEmployees();
        Double totalPayroll = employeeRepository.calculateTotalPayroll();
        Double avgSalary = employeeRepository.calculateAverageSalary();
        var departmentBreakdown = employeeRepository.getDepartmentDistribution();

        return new DashboardStats(total, active, onLeave, totalPayroll, avgSalary, departmentBreakdown);
    }

    public List<Employee> seedInitialData() {
        if (employeeRepository.count() > 0) {
            return employeeRepository.findAll();
        }

        List<Employee> seedList = new ArrayList<>();
        seedList.add(new Employee("Alex", "Morgan", "alex.morgan@worknest.io", "+1 (555) 234-5678", "Engineering", "Senior Full-Stack Engineer", 95000.0, "ACTIVE", LocalDate.of(2023, 1, 15), "San Francisco, CA", "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150&auto=format&fit=crop&q=80"));
        seedList.add(new Employee("David", "Chen", "david.chen@worknest.io", "+1 (555) 345-6789", "Engineering", "Lead DevOps Engineer", 110000.0, "ACTIVE", LocalDate.of(2022, 6, 1), "Austin, TX", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=150&auto=format&fit=crop&q=80"));
        seedList.add(new Employee("Sophia", "Rodriguez", "sophia.rodriguez@worknest.io", "+1 (555) 456-7890", "Human Resources", "HR Business Partner", 72000.0, "ACTIVE", LocalDate.of(2023, 3, 20), "New York, NY", "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150&auto=format&fit=crop&q=80"));
        seedList.add(new Employee("Marcus", "Johnson", "marcus.johnson@worknest.io", "+1 (555) 567-8901", "Product", "Principal Product Manager", 115000.0, "ACTIVE", LocalDate.of(2022, 11, 10), "Seattle, WA", "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=150&auto=format&fit=crop&q=80"));
        seedList.add(new Employee("Elena", "Rostova", "elena.rostova@worknest.io", "+1 (555) 678-9012", "Marketing", "Growth Marketing Director", 88000.0, "ON_LEAVE", LocalDate.of(2023, 8, 5), "Chicago, IL", "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=150&auto=format&fit=crop&q=80"));
        seedList.add(new Employee("James", "Wilson", "james.wilson@worknest.io", "+1 (555) 789-0123", "Finance", "Financial Controller", 92000.0, "ACTIVE", LocalDate.of(2021, 9, 14), "Boston, MA", "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=150&auto=format&fit=crop&q=80"));
        seedList.add(new Employee("Priya", "Patel", "priya.patel@worknest.io", "+1 (555) 890-1234", "Engineering", "Frontend Specialist", 82000.0, "ACTIVE", LocalDate.of(2024, 2, 1), "San Jose, CA", "https://images.unsplash.com/photo-1580489944761-15a19d654956?w=150&auto=format&fit=crop&q=80"));
        seedList.add(new Employee("Lucas", "Almeida", "lucas.almeida@worknest.io", "+1 (555) 901-2345", "Operations", "Operations Manager", 78000.0, "INACTIVE", LocalDate.of(2022, 4, 18), "Miami, FL", "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=150&auto=format&fit=crop&q=80"));

        return employeeRepository.saveAll(seedList);
    }
}
