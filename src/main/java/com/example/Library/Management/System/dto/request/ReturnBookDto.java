package com.example.Library.Management.System.dto.request;

import java.sql.Date;

public class ReturnBookDto {

    private String memberid;
    private String bookid;
    private Date recived_date;


    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public Date getRecived_date() {
        return recived_date;
    }

    public void setRecived_date(Date recived_date) {
        this.recived_date = recived_date;
    }

    @Override
    public String toString() {
        return "ReturnBookDto{" +
                "memberid='" + memberid + '\'' +
                ", bookid='" + bookid + '\'' +
                ", recived_date=" + recived_date +
                '}';
    }
}
