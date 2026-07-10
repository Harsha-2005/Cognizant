package com.library.boot.repository;

import com.library.boot.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Exercise 9: Spring Data JPA repository.
 *
 * We get CRUD for free by extending JpaRepository — no SQL, no boilerplate.
 * Adding a couple of custom finders to show how derived query methods work.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Spring Data generates the query from the method name — no @Query needed
    List<Book> findByAuthor(String author);

    List<Book> findByAvailableTrue();
}
