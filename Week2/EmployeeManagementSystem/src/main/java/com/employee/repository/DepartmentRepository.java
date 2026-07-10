package com.employee.repository;

import com.employee.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Exercise 3: Department repository.
 *
 * JpaRepository gives us save, findById, findAll, delete etc. for free.
 * We just add the custom finders we actually need.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Exercise 3: derived query — Spring generates the SQL from the method name
    Optional<Department> findByName(String name);

    boolean existsByName(String name);
}
