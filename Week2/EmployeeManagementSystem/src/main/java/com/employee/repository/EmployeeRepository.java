package com.employee.repository;

import com.employee.entity.Employee;
import com.employee.projection.EmployeeDTO;
import com.employee.projection.EmployeeSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Exercise 3: Employee repository with CRUD out of the box.
 *
 * Exercise 5: Custom queries using both derived method names and @Query.
 * Exercise 6: Pageable support for paginated + sorted results.
 * Exercise 8: Projection return types — interface-based and class-based.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // ── Exercise 3: derived queries ──────────────────────────────────────────

    Optional<Employee> findByEmail(String email);

    List<Employee> findByDepartmentId(Long departmentId);

    List<Employee> findByNameContainingIgnoreCase(String name);

    // ── Exercise 5: @Query annotation (JPQL) ─────────────────────────────────

    // Custom JPQL — useful when the derived name would be too long or confusing
    @Query("SELECT e FROM Employee e WHERE e.department.name = :deptName")
    List<Employee> findByDepartmentName(@Param("deptName") String deptName);

    // Class-based projection via constructor expression (Exercise 8)
    @Query("SELECT new com.employee.projection.EmployeeDTO(e.id, e.name, e.email, e.department.name) " +
           "FROM Employee e")
    List<EmployeeDTO> findAllAsDTO();

    // Exercise 5: native SQL query (when JPQL won't cut it)
    @Query(value = "SELECT * FROM employees WHERE email LIKE %:domain%",
           nativeQuery = true)
    List<Employee> findByEmailDomain(@Param("domain") String domain);

    // ── Exercise 5: named query (defined on the entity with @NamedQuery) ─────

    // The name must match: EntityName.methodName → Employee.findByDepartmentName
    // But since we already have a JPQL one above with the same semantic,
    // we call the @NamedQuery using a different signature:
    @Query(name = "Employee.findByDepartmentName")
    List<Employee> findByDepartmentNamedQuery(@Param("departmentName") String departmentName);

    // ── Exercise 6: pagination + sorting ─────────────────────────────────────

    // Pageable carries page number, size, and sort direction
    Page<Employee> findAll(Pageable pageable);

    Page<Employee> findByDepartmentId(Long departmentId, Pageable pageable);

    // ── Exercise 8: interface-based projection ────────────────────────────────

    List<EmployeeSummary> findAllProjectedBy();
}
