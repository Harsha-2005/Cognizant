package com.employee.controller;

import com.employee.entity.Department;
import com.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Exercise 4: REST controller for Department CRUD.
 *
 * Kept intentionally thin — delegates everything to the service layer
 * rather than calling repositories directly from the controller.
 *
 * Base URL: /api/departments
 */
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final EmployeeService employeeService;

    public DepartmentController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Department> getAll() {
        return employeeService.getAllDepartments();
    }

    @PostMapping
    public ResponseEntity<Department> create(@RequestBody Department department) {
        Department saved = employeeService.createDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
