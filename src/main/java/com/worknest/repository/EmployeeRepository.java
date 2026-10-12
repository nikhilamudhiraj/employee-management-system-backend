package com.worknest.repository;

import com.worknest.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartmentIgnoreCase(String department);

    List<Employee> findByStatusIgnoreCase(String status);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    @Query("SELECT e FROM Employee e WHERE " +
           "(:query IS NULL OR :query = '' OR " +
           "LOWER(e.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(e.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(e.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(e.designation) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(e.department) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
           "(:department IS NULL OR :department = '' OR LOWER(e.department) = LOWER(:department)) AND " +
           "(:status IS NULL OR :status = '' OR LOWER(e.status) = LOWER(:status)) " +
           "ORDER BY e.createdAt DESC")
    List<Employee> searchAndFilter(
            @Param("query") String query,
            @Param("department") String department,
            @Param("status") String status);

    @Query("SELECT COUNT(e) FROM Employee e WHERE UPPER(e.status) = 'ACTIVE'")
    long countActiveEmployees();

    @Query("SELECT COUNT(e) FROM Employee e WHERE UPPER(e.status) = 'ON_LEAVE'")
    long countOnLeaveEmployees();

    @Query("SELECT COALESCE(SUM(e.salary), 0) FROM Employee e WHERE UPPER(e.status) = 'ACTIVE'")
    Double calculateTotalPayroll();

    @Query("SELECT COALESCE(AVG(e.salary), 0) FROM Employee e WHERE UPPER(e.status) = 'ACTIVE'")
    Double calculateAverageSalary();

    @Query("SELECT e.department AS department, COUNT(e) AS count FROM Employee e GROUP BY e.department")
    List<Map<String, Object>> getDepartmentDistribution();
}
