package com.example.Library.Management.System.entity;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private int reportId;

    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    // Constructors
    public Report() {}

    public Report(Date issueDate, Date dueDate, Member member, Book book) {
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.member = member;
        this.book = book;
    }

    // Getters and Setters
    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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
}
