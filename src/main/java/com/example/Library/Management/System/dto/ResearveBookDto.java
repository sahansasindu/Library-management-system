package com.example.Library.Management.System.dto;


import java.sql.Date;

public class ResearveBookDto {



    private String member_id;
    private String book_id;
    private Date reseaved_date;

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public Date getReseaved_date() {
        return reseaved_date;
    }

    public void setReseaved_date(Date reseaved_date) {
        this.reseaved_date = reseaved_date;
    }
}
