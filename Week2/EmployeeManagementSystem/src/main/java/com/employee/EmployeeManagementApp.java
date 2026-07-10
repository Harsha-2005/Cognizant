package com.employee;

import com.employee.entity.Department;
import com.employee.entity.Employee;
import com.employee.repository.DepartmentRepository;
import com.employee.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

/**
 * Exercise 1: Spring Boot entry point for the Employee Management System.
 *
 * @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan
 *
 * The CommandLineRunner below seeds the H2 database with sample data on
 * every startup so there's something real to query against.
 *
 * To run:
 *   mvn spring-boot:run
 *
 * Then try:
 *   GET  http://localhost:8080/api/employees
 *   GET  http://localhost:8080/api/employees/paged?page=0&size=3&sortBy=name
 *   GET  http://localhost:8080/api/employees/summary
 *   GET  http://localhost:8080/api/departments
 *   GET  http://localhost:8080/h2-console   (JDBC URL: jdbc:h2:mem:testdb)
 */
@SpringBootApplication
public class EmployeeManagementApp {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApp.class, args);
    }

    /**
     * Seed some departments and employees so the app has data to work with.
     * This runs right after the application context is fully started.
     */
    @Bean
    CommandLineRunner seedData(DepartmentRepository deptRepo,
                               EmployeeRepository   empRepo) {
        return args -> {
            // Create departments
            Department engineering = deptRepo.save(new Department("Engineering"));
            Department hr          = deptRepo.save(new Department("Human Resources"));
            Department finance     = deptRepo.save(new Department("Finance"));

            // Create employees and assign to departments
            List<Employee> employees = Arrays.asList(
                new Employee("Alice Johnson",  "alice@company.com",   engineering),
                new Employee("Bob Smith",      "bob@company.com",     engineering),
                new Employee("Carol Davis",    "carol@company.com",   hr),
                new Employee("David Wilson",   "david@company.com",   hr),
                new Employee("Eve Martinez",   "eve@company.com",     finance),
                new Employee("Frank Brown",    "frank@company.com",   engineering)
            );

            empRepo.saveAll(employees);

            System.out.println("─────────────────────────────────────────");
            System.out.println("EMS started. Sample data loaded.");
            System.out.println("Departments: " + deptRepo.count());
            System.out.println("Employees  : " + empRepo.count());
            System.out.println("API base   : http://localhost:8080/api");
            System.out.println("H2 console : http://localhost:8080/h2-console");
            System.out.println("─────────────────────────────────────────");
        };
    }
}
