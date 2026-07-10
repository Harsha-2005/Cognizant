package com.employee.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Exercise 2: Employee entity.
 *
 * Fields: id, name, email, department (FK to departments table).
 *
 * Exercise 5: @NamedQuery defined here — a named query lives with the entity
 * so it's easy to find. We execute it from the repository.
 *
 * Exercise 7: Spring auditing tracks who created/modified each record and when.
 * @CreatedBy / @LastModifiedBy need an AuditorAware bean to supply the user.
 *
 * Exercise 10: @DynamicUpdate tells Hibernate to only include changed columns
 * in UPDATE statements instead of updating every column every time.
 */
@Entity
@Table(name = "employees")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate  // Ex 10: only update columns that actually changed
@NamedQuery(
    name  = "Employee.findByDepartmentName",
    query = "SELECT e FROM Employee e WHERE e.department.name = :departmentName"
)
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // Exercise 2: many employees belong to one department
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    // Exercise 7: who created this record (populated by AuditorAwareImpl)
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    // Exercise 7: who last changed this record
    @LastModifiedBy
    private String lastModifiedBy;

    // Exercise 7: timestamp fields — set automatically by Spring Data auditing
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public Employee(String name, String email, Department department) {
        this.name       = name;
        this.email      = email;
        this.department = department;
    }
}
