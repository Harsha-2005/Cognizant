package com.library.model;

/**
 * Simple Book domain object.
 * Keeping it plain — no JPA annotations here since this is the non-Boot version.
 * The Boot/JPA version lives in com.library.boot.model.
 */
public class Book {

    private Long id;
    private String title;
    private String author;
    private boolean available;

    public Book() {}

    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;
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
        return "Book{id=" + id + ", title='" + title + "', author='" + author + "', available=" + available + "}";
    }
}
