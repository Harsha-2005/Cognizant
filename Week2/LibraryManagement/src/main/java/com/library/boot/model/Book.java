package com.library.boot.model;

import javax.persistence.*;

/**
 * JPA entity for Exercise 9.
 *
 * Mapped to the BOOKS table in H2 (auto-created by Hibernate on startup).
 * Using @GeneratedValue so we don't have to worry about ID management ourselves.
 */
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private boolean available = true;

    // JPA needs a no-arg constructor
    public Book() {}

    public Book(String title, String author) {
        this.title  = title;
        this.author = author;
    }

    public Long getId()           { return id; }
    public String getTitle()      { return title; }
    public String getAuthor()     { return author; }
    public boolean isAvailable()  { return available; }

    public void setId(Long id)              { this.id = id; }
    public void setTitle(String title)      { this.title = title; }
    public void setAuthor(String author)    { this.author = author; }
    public void setAvailable(boolean a)     { this.available = a; }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', author='" + author + "'}";
    }
}
