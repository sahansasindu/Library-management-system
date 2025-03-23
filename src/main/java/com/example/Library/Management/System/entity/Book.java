package com.example.Library.Management.System.entity;

import jakarta.persistence.*;
@Entity
@Table(name = "book")
public class   Book {

    @Id
    @Column(name = "bookid", nullable = false, unique = true)
    private String bookId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "qty", nullable = false)
    private int qty;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "activestate", nullable = false)
    private Boolean active_state = true;


    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public Boolean getActive_state() {
        return active_state;
    }

    public void setActive_state(Boolean active_state) {
        this.active_state = active_state;
    }
}
