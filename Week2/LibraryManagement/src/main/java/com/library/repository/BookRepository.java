package com.library.repository;

import com.library.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BookRepository handles all data access for books.
 *
 * Using an in-memory map to simulate a database — keeps things
 * self-contained without needing an actual DB for these exercises.
 *
 * Exercise 1: defined as a bean in applicationContext.xml
 * Exercise 6: @Repository makes Spring auto-detect this via component scan
 */
@Repository
public class BookRepository {

    // Simulated in-memory store
    private final Map<Long, Book> store = new HashMap<>();

    public BookRepository() {
        // Pre-load some books so the app has something to work with
        store.put(1L, new Book(1L, "Clean Code", "Robert C. Martin"));
        store.put(2L, new Book(2L, "The Pragmatic Programmer", "Andy Hunt"));
        store.put(3L, new Book(3L, "Design Patterns", "Gang of Four"));
    }

    public Book findById(Long id) {
        return store.get(id);
    }

    public List<Book> findAll() {
        return new ArrayList<>(store.values());
    }

    public void save(Book book) {
        store.put(book.getId(), book);
    }

    public void deleteById(Long id) {
        store.remove(id);
    }
}
