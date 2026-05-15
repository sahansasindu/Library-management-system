package com.example.Library.Management.System.entity;

import jakarta.persistence.*;

@Entity
@Table(name="notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notification_Id;

    private String message;
}
