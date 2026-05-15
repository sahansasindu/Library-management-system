package com.example.Library.Management.System.entity;

import com.example.Library.Management.System.enums.TransectionType;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="ransection")
public class Transection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ransection_id;

    private String name;

    private Date date;

    @Enumerated(EnumType.STRING)
    private TransectionType type;

    public Transection() {
    }

    public Transection(String name, Date date, TransectionType type) {
        this.name = name;
        this.date = date;
        this.type = type;
    }

    public long getRansection_id() {
        return ransection_id;
    }

    public void setRansection_id(long ransection_id) {
        this.ransection_id = ransection_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public TransectionType getType() {
        return type;
    }

    public void setType(TransectionType type) {
        this.type = type;
    }
}
