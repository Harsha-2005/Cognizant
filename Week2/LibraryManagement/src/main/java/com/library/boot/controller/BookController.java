package com.library.boot.controller;

import com.library.boot.model.Book;
import com.library.boot.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Exercise 9: REST controller for the Book resource.
 *
 * Handles all CRUD operations over HTTP.
 * Keeping the service layer inline here to avoid over-engineering for a demo,
 * but in a real project this would delegate to a BookService.
 *
 * Endpoints:
 *   GET    /api/books          — list all books
 *   GET    /api/books/{id}     — get one book
 *   POST   /api/books          — create a book
 *   PUT    /api/books/{id}     — update a book
 *   DELETE /api/books/{id}     — delete a book
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRepository bookRepository;

    // Constructor injection — preferred over field injection with @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book saved = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @RequestBody Book incoming) {
        return bookRepository.findById(id).map(existing -> {
            existing.setTitle(incoming.getTitle());
            existing.setAuthor(incoming.getAuthor());
            existing.setAvailable(incoming.isAvailable());
            return ResponseEntity.ok(bookRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
