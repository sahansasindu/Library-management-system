package com.example.Library.Management.System.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long book_id;
    @Column(name="title",nullable = false)
    private String title;
    @Column(name="author",nullable = false)
    private String author;
    @Column(name="isbn",nullable = false)
    private String isbn;
    @Column(name="catagory",nullable = false)
    private String catagory;
    @Column(name="location",nullable = false)
    private String location;
    @Column(name="qty",nullable = false)
    private int qty;


}
