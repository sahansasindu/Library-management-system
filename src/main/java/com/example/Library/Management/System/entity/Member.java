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

    public Member() {
    }

    public Member(String member_id, String first_name, String last_name, String address, Date dob, String nic_no, String phone_no) {
        this.member_id = member_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.dob = dob;
        this.nic_no = nic_no;
        this.phone_no = phone_no;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getNic_no() {
        return nic_no;
    }

    public void setNic_no(String nic_no) {
        this.nic_no = nic_no;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}