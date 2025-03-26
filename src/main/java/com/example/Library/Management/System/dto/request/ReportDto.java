package com.example.Library.Management.System.dto.request;

import com.example.Library.Management.System.entity.Book;
import com.example.Library.Management.System.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.sql.Date;

public class ReportDto {


    private String member_id;

    private String book_id;



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

    @Override
    public String toString() {
        return "ReportDto{" +
                "member_id='" + member_id + '\'' +
                ", book_id='" + book_id + '\'' +
                '}';
    }
}
