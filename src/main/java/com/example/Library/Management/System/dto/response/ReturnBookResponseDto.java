package com.example.Library.Management.System.dto.response;

import java.sql.Date;

public class ReturnBookResponseDto {

    private String book_id;
    private String member_id;

    private Date recived_date;
    private double penalty_amount;

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
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

    @Override
    public String toString() {
        return "ReturnBookResponcseDto{" +
                "book_id='" + book_id + '\'' +
                ", member_id='" + member_id + '\'' +
                ", recived_date=" + recived_date +
                ", penalty_amount=" + penalty_amount +
                '}';
    }
}
