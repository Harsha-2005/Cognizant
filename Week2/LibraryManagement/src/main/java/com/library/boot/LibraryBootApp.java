package com.library.boot;

import com.library.boot.model.Book;
import com.library.boot.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Exercise 9: Spring Boot entry point.
 *
 * @SpringBootApplication is shorthand for:
 *   @Configuration + @EnableAutoConfiguration + @ComponentScan
 *
 * The CommandLineRunner bean seeds the H2 database with some
 * sample data so the REST endpoints have something to return.
 *
 * Run this class and then test with:
 *   GET  http://localhost:8080/api/books
 *   POST http://localhost:8080/api/books   (body: {"title":"...","author":"..."})
 */
@SpringBootApplication
public class LibraryBootApp {

    public static void main(String[] args) {
        SpringApplication.run(LibraryBootApp.class, args);
    }

    // Seed data on startup — handy for demos, you'd remove this in prod
    @Bean
    CommandLineRunner seedData(BookRepository repo) {
        return args -> {
            repo.save(new Book("Clean Code", "Robert C. Martin"));
            repo.save(new Book("The Pragmatic Programmer", "Andy Hunt"));
            repo.save(new Book("Design Patterns", "Gang of Four"));
            System.out.println("Sample books loaded into H2. "
                    + "Visit http://localhost:8080/api/books");
        };
    }
}
