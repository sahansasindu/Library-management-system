package com.example.Library.Management.System.dto.response;

public class FineDto {
    private long returnBookId;
    private String memberId;
    private String memberName;
    private String bookId;
    private String bookTitle;
    private double amount;
    private boolean isPaid;

    public FineDto() {}

    public FineDto(long returnBookId, String memberId, String memberName, String bookId, String bookTitle, double amount, boolean isPaid) {
        this.returnBookId = returnBookId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.amount = amount;
        this.isPaid = isPaid;
    }

    public long getReturnBookId() {
        return returnBookId;
    }

    public void setReturnBookId(long returnBookId) {
        this.returnBookId = returnBookId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
