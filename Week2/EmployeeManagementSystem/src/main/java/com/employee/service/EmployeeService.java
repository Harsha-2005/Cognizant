package com.employee.service;

import com.employee.entity.Department;
import com.employee.entity.Employee;
import com.employee.projection.EmployeeDTO;
import com.employee.projection.EmployeeSummary;
import com.employee.repository.DepartmentRepository;
import com.employee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * EmployeeService: business logic layer for employee and department operations.
 *
 * @Transactional on the class means every public method runs in a transaction
 * by default. Read-only methods get readOnly=true so Hibernate can skip
 * dirty checking on them — small but free performance win.
 *
 * Exercise 4: CRUD operations
 * Exercise 6: Pagination and sorting
 * Exercise 10: Batch save for bulk inserts
 */
@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository   employeeRepo;
    private final DepartmentRepository departmentRepo;

    public EmployeeService(EmployeeRepository employeeRepo,
                           DepartmentRepository departmentRepo) {
        this.employeeRepo   = employeeRepo;
        this.departmentRepo = departmentRepo;
    }

    // ── Exercise 4: CRUD ─────────────────────────────────────────────────────

    public Employee createEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    public Employee updateEmployee(Long id, Employee incoming) {
        Employee existing = getEmployeeById(id);
        existing.setName(incoming.getName());
        existing.setEmail(incoming.getEmail());
        if (incoming.getDepartment() != null) {
            existing.setDepartment(incoming.getDepartment());
        }
        // No explicit save() needed — Hibernate detects the dirty entity
        // and flushes the UPDATE at the end of the transaction
        return existing;
    }

    public void deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
    }

    public Department createDepartment(Department department) {
        return departmentRepo.save(department);
    }

    @Transactional(readOnly = true)
    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }

    // ── Exercise 6: Pagination + Sorting ────────────────────────────────────

    /**
     * Returns a page of employees sorted by the given field.
     * PageRequest.of() is the clean way to build a Pageable in Spring Data.
     */
    @Transactional(readOnly = true)
    public Page<Employee> getEmployeesPaged(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return employeeRepo.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Employee> getEmployeesByDepartmentPaged(Long deptId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return employeeRepo.findByDepartmentId(deptId, pageable);
    }

    // ── Exercise 8: Projections ──────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<EmployeeSummary> getEmployeeSummaries() {
        return employeeRepo.findAllProjectedBy();
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeeDTOs() {
        return employeeRepo.findAllAsDTO();
    }

    // ── Exercise 10: Batch processing ────────────────────────────────────────

    /**
     * Inserts a large list of employees in batches.
     * With hibernate.jdbc.batch_size=20 in application.properties,
     * Hibernate groups the INSERTs so we don't hammer the DB with
     * one query per row.
     */
    public void batchInsertEmployees(List<Employee> employees) {
        int batchSize = 20;
        List<Employee> batch = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++) {
            batch.add(employees.get(i));
            if (batch.size() == batchSize || i == employees.size() - 1) {
                employeeRepo.saveAll(batch);
                batch.clear();
                // In a real Hibernate setup you'd flush/clear the session here
                // but Spring Data's saveAll handles that internally
            }
        }
        System.out.println("Batch insert complete. Total records: " + employees.size());
    }
}
