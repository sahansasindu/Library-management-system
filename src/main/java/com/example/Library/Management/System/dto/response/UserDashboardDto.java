package com.example.Library.Management.System.dto.response;

import java.util.List;

public class UserDashboardDto {

    private List<ReservedBookItem> reservedBooks;
    private List<IssuedBookItem> issuedBooks;
    private List<ReturnedBookItem> returnedBooks;
    private double totalFines;

    public UserDashboardDto() {}

    public UserDashboardDto(List<ReservedBookItem> reservedBooks, List<IssuedBookItem> issuedBooks,
                             List<ReturnedBookItem> returnedBooks, double totalFines) {
        this.reservedBooks = reservedBooks;
        this.issuedBooks = issuedBooks;
        this.returnedBooks = returnedBooks;
        this.totalFines = totalFines;
    }

    public List<ReservedBookItem> getReservedBooks() { return reservedBooks; }
    public void setReservedBooks(List<ReservedBookItem> reservedBooks) { this.reservedBooks = reservedBooks; }

    public List<IssuedBookItem> getIssuedBooks() { return issuedBooks; }
    public void setIssuedBooks(List<IssuedBookItem> issuedBooks) { this.issuedBooks = issuedBooks; }

    public List<ReturnedBookItem> getReturnedBooks() { return returnedBooks; }
    public void setReturnedBooks(List<ReturnedBookItem> returnedBooks) { this.returnedBooks = returnedBooks; }

    public double getTotalFines() { return totalFines; }
    public void setTotalFines(double totalFines) { this.totalFines = totalFines; }

    // --- Inner DTOs ---

    public static class ReservedBookItem {
        private String bookId;
        private String title;
        private String author;
        private String reservedDate;
        private String dueDate;
        private Boolean state;

        public ReservedBookItem() {}

        public ReservedBookItem(String bookId, String title, String author,
                                String reservedDate, String dueDate, Boolean state) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
            this.reservedDate = reservedDate;
            this.dueDate = dueDate;
            this.state = state;
        }

        public String getBookId() { return bookId; }
        public void setBookId(String bookId) { this.bookId = bookId; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
        public String getReservedDate() { return reservedDate; }
        public void setReservedDate(String reservedDate) { this.reservedDate = reservedDate; }
        public String getDueDate() { return dueDate; }
        public void setDueDate(String dueDate) { this.dueDate = dueDate; }
        public Boolean getState() { return state; }
        public void setState(Boolean state) { this.state = state; }
    }

    public static class IssuedBookItem {
        private String bookId;
        private String title;
        private String author;
        private String issueDate;
        private String dueDate;
        private Boolean isReturned;

        public IssuedBookItem() {}

        public IssuedBookItem(String bookId, String title, String author, String issueDate, String dueDate, Boolean isReturned) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
            this.issueDate = issueDate;
            this.dueDate = dueDate;
            this.isReturned = isReturned;
        }

        public String getBookId() { return bookId; }
        public void setBookId(String bookId) { this.bookId = bookId; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
        public String getIssueDate() { return issueDate; }
        public void setIssueDate(String issueDate) { this.issueDate = issueDate; }
        public String getDueDate() { return dueDate; }
        public void setDueDate(String dueDate) { this.dueDate = dueDate; }
        public Boolean getIsReturned() { return isReturned; }
        public void setIsReturned(Boolean isReturned) { this.isReturned = isReturned; }
    }

    public static class ReturnedBookItem {
        private String bookId;
        private String title;
        private String author;
        private String returnedDate;
        private double penaltyAmount;
        private Boolean isPaid;

        public ReturnedBookItem() {}

        public ReturnedBookItem(String bookId, String title, String author,
                                String returnedDate, double penaltyAmount, Boolean isPaid) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
            this.returnedDate = returnedDate;
            this.penaltyAmount = penaltyAmount;
            this.isPaid = isPaid;
        }

        public String getBookId() { return bookId; }
        public void setBookId(String bookId) { this.bookId = bookId; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
        public String getReturnedDate() { return returnedDate; }
        public void setReturnedDate(String returnedDate) { this.returnedDate = returnedDate; }
        public double getPenaltyAmount() { return penaltyAmount; }
        public void setPenaltyAmount(double penaltyAmount) { this.penaltyAmount = penaltyAmount; }
        public Boolean getIsPaid() { return isPaid; }
        public void setIsPaid(Boolean isPaid) { this.isPaid = isPaid; }
    }
}
