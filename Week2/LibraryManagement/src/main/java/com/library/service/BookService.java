package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BookService sits between the controller/main and the repository layer.
 * It's where the actual business logic lives.
 *
 * Exercise 2 & 5: Setter injection — Spring calls setBookRepository() after
 *                  constructing the bean (configured via <property> in XML).
 *
 * Exercise 6: @Service — Spring auto-detects this during component scan
 *              so we don't need an explicit <bean> declaration in the XML.
 *
 * Exercise 7: Constructor injection is also supported — see the constructor below.
 *              Spring will prefer the constructor if <constructor-arg> is present in XML.
 */
@Service
public class BookService {

    private BookRepository bookRepository;

    // Exercise 7: constructor injection
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Exercise 2 & 5 & 7: setter injection
    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        return book;
    }

    public void addBook(Book book) {
        bookRepository.save(book);
        System.out.println("Book added: " + book.getTitle());
    }

    public void removeBook(Long id) {
        bookRepository.deleteById(id);
        System.out.println("Book removed: id=" + id);
    }
}
