package com.employee.controller;

import com.employee.entity.Employee;
import com.employee.projection.EmployeeDTO;
import com.employee.projection.EmployeeSummary;
import com.employee.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Exercise 4: REST controller for Employee CRUD.
 * Exercise 6: Paginated + sorted endpoints.
 * Exercise 8: Projection endpoints.
 *
 * All responses are JSON — @RestController handles that automatically.
 *
 * Base URL: /api/employees
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ── CRUD ─────────────────────────────────────────────────────────────────

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(employeeService.getEmployeeById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        Employee saved = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id,
                                           @RequestBody Employee employee) {
        try {
            return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // ── Exercise 6: Pagination + Sorting ─────────────────────────────────────

    /**
     * GET /api/employees/paged?page=0&size=5&sortBy=name
     * Returns one page of employees sorted by the requested field.
     */
    @GetMapping("/paged")
    public Page<Employee> getPaged(
            @RequestParam(defaultValue = "0")    int    page,
            @RequestParam(defaultValue = "5")    int    size,
            @RequestParam(defaultValue = "name") String sortBy) {
        return employeeService.getEmployeesPaged(page, size, sortBy);
    }

    // ── Exercise 8: Projections ──────────────────────────────────────────────

    // Interface-based — lightweight, only name/email/dept
    @GetMapping("/summary")
    public List<EmployeeSummary> getSummaries() {
        return employeeService.getEmployeeSummaries();
    }

    // Class-based DTO
    @GetMapping("/dto")
    public List<EmployeeDTO> getDTOs() {
        return employeeService.getEmployeeDTOs();
    }
}
