package com.example.Library.Management.System.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name="reseavebook")
public class ReseaveBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reseave_id")
    private long reseave_id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name="reseaved_date")
    private Date reseaved_date;

}