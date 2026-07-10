package com.employee.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 2: Department entity.
 *
 * One department can have many employees — we model that with a
 * @OneToMany here and a @ManyToOne on the Employee side.
 *
 * Exercise 7: @EntityListeners(AuditingEntityListener.class) wires in
 * Spring's auditing so @CreatedDate / @LastModifiedDate fill automatically.
 *
 * Exercise 10: @BatchSize(size = 20) tells Hibernate to load the employees
 * collection in batches instead of one query per department (avoids N+1).
 */
@Entity
@Table(name = "departments")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Exercise 2: bi-directional one-to-many
    // mappedBy points to the field in Employee that owns the FK
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 20)  // Ex 10: batch-load employees to avoid N+1
    private List<Employee> employees = new ArrayList<>();

    // Exercise 7: auditing fields
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public Department(String name) {
        this.name = name;
    }
}
