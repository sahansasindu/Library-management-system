package com.example.Library.Management.System.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name="reseavebook")
public class ReseaveBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserve_id")
    private long reserveId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "reserved_date", nullable = false)
    private Date reservedDate;

    @Column(name = "state", nullable = false)
    private Boolean state = true;


    public ReseaveBook() {

    }

    public ReseaveBook(Member member, Book book, Date reservedDate) {
        this.member = member;
        this.book = book;
        this.reservedDate = reservedDate;

    }


    // Getters and Setters
    public long getReserveId() {
        return reserveId;
    }

    public void setReserveId(long reserveId) {
        this.reserveId = reserveId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(Date reservedDate) {
        this.reservedDate = reservedDate;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}