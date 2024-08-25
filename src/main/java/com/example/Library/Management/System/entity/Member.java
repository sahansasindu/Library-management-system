package com.example.Library.Management.System.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name="member")
public class Member {

    @Id
    @Column(name="member_id",nullable = false,unique = true)
    private String member_id;
    @Column(name="first_name",nullable = false)
    private String first_name;
    @Column(name="last_name",nullable = false)
    private String last_name;
    @Column(name="address",nullable = false)
    private String address;
    @Column(name="dob",nullable = false)
    private Date dob;
    @Column(name="nic",nullable = false,unique = true)
    private String nic_no;
    @Column(name="phone_no",nullable = false,unique = true)
    private String phone_no;


}
