package com.example.Library.Management.System.entity;

import jakarta.persistence.*;
import lombok.Builder;

@Builder
@Entity
@Table(name="conditions")

public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="condition_id")
    private int condition_id;

    @Column(name="entry_payment")
    private double entry_payment;

    @Column(name="penalty_cost")
    private double penalty_cost;

    public Condition() {

    }

    public double getEntry_payment() {
        return entry_payment;
    }

    public void setEntry_payment(double entry_payment) {
        this.entry_payment = entry_payment;
    }

    public double getPenalty_cost() {
        return penalty_cost;
    }

    public void setPenalty_cost(double penalty_cost) {
        this.penalty_cost = penalty_cost;
    }
}
