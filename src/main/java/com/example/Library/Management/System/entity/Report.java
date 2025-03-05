package com.example.Library.Management.System.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name="report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="report_id")
    private int report_id;
    @Column(name="issue_date",nullable = false)
    private Date issue_date;

    @Column(name="due_date",nullable = false)
    private Date due_date;
    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    private Book book;


}
