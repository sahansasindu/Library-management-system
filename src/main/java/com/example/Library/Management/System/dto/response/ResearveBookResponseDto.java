package com.example.Library.Management.System.dto.response;

import java.sql.Date;

public class ResearveBookResponseDto {

    private String member_id;

    private String book_id;

    private Date reservedDate;

    private Boolean state = true;




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

    @Override
    public String toString() {
        return "ResearveBookResponseDto{" +
                "member_id='" + member_id + '\'' +
                ", book_id='" + book_id + '\'' +
                ", reservedDate=" + reservedDate +
                ", state=" + state +
                '}';
    }
}
