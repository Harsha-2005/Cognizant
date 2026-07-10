package com.employee.projection;

/**
 * Exercise 8: Class-based (DTO) projection.
 *
 * Unlike the interface projection, this is a plain class — useful when
 * you need to add custom logic or the interface approach gets awkward.
 * Spring Data matches the constructor parameter names to query aliases.
 */
public class EmployeeDTO {

    private final Long   id;
    private final String name;
    private final String email;
    private final String departmentName;

    // Spring Data calls this constructor when the query returns results
    public EmployeeDTO(Long id, String name, String email, String departmentName) {
        this.id             = id;
        this.name           = name;
        this.email          = email;
        this.departmentName = departmentName;
    }

    public Long   getId()             { return id; }
    public String getName()           { return name; }
    public String getEmail()          { return email; }
    public String getDepartmentName() { return departmentName; }

    @Override
    public String toString() {
        return "EmployeeDTO{id=" + id + ", name='" + name
                + "', email='" + email + "', dept='" + departmentName + "'}";
    }
}
