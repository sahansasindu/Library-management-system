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

    public ReturnBook() {
    }

    public ReturnBook(Book book, Member member, Date recived_date, double penalty_amount) {
        this.book = book;
        this.member = member;
        this.recived_date = recived_date;
        this.penalty_amount = penalty_amount;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Date getRecived_date() {
        return recived_date;
    }

    public void setRecived_date(Date recived_date) {
        this.recived_date = recived_date;
    }

    public double getPenalty_amount() {
        return penalty_amount;
    }

    public void setPenalty_amount(double penalty_amount) {
        this.penalty_amount = penalty_amount;
    }
}
