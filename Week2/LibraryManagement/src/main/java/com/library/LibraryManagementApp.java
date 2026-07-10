package com.library;

import com.library.model.Book;
import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Entry point for exercises 1–8 (non-Boot Spring application).
 *
 * Loads the Spring context from the XML file, grabs the BookService bean,
 * and runs a few operations to prove everything is wired up correctly.
 *
 * Covers:
 *  - Ex 1: Spring context loads, beans exist
 *  - Ex 2: BookRepository injected into BookService (setter injection)
 *  - Ex 3: AOP logs execution time for each service call
 *  - Ex 6: @Service / @Repository annotations work alongside XML config
 *  - Ex 7: Constructor + setter injection both configured
 *  - Ex 8: @Before / @After advice fires around service methods
 */
public class LibraryManagementApp {

    public static void main(String[] args) {

        // Load the Spring IoC container from our XML config
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        BookService bookService = context.getBean("bookService", BookService.class);

        System.out.println("\n=== Library Management System ===\n");

        // List all pre-loaded books
        System.out.println("--- All Books ---");
        bookService.getAllBooks().forEach(System.out::println);

        // Add a new book
        System.out.println("\n--- Adding a new book ---");
        bookService.addBook(new Book(4L, "Refactoring", "Martin Fowler"));

        // Fetch a single book by ID
        System.out.println("\n--- Fetching book id=1 ---");
        Book b = bookService.getBookById(1L);
        System.out.println("Found: " + b);

        // Remove a book
        System.out.println("\n--- Removing book id=2 ---");
        bookService.removeBook(2L);

        System.out.println("\n--- Books after removal ---");
        bookService.getAllBooks().forEach(System.out::println);

        // Close context to release resources (triggers destroy callbacks if any)
        ((ClassPathXmlApplicationContext) context).close();
    }
}
