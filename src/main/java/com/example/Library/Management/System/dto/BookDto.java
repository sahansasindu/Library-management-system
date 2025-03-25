package com.example.Library.Management.System.dto;

public class BookDto {

    private String bookid;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private int qty;
    private String photoBase64; // Change to Base64

    // Getters and Setters
    public String getBookid() { return bookid; }
    public void setBookid(String bookid) { this.bookid = bookid; }

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

    public String getPhotoBase64() { return photoBase64; }
    public void setPhotoBase64(String photoBase64) { this.photoBase64 = photoBase64; }
}