package com.example.Library.Management.System.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "returnbook")
public class ReturnBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="returnbook_id")
    private long returnbook_id;
    @ManyToOne
    @JoinColumn(name = "book_id",nullable = false)
    private Book book;
    @ManyToOne
    @JoinColumn(name="member_id",nullable = false)
    private Member member;

    @Column(name="recived_date",nullable = false)
    private Date recived_date;

    @Column(name="penalty_amount")
    private double penalty_amount;
}
