package com.employee.projection;

/**
 * Exercise 8: Interface-based projection for Employee.
 *
 * Spring Data creates a proxy at runtime that only fetches the columns
 * we declare here — cheaper than loading the full entity when you only
 * need a summary view (e.g., a dropdown list or a search result).
 */
public interface EmployeeSummary {
    Long   getId();
    String getName();
    String getEmail();

    // Nested projection — pulls the department name without loading
    // the full Department entity graph
    DepartmentInfo getDepartment();

    interface DepartmentInfo {
        String getName();
    }
}
