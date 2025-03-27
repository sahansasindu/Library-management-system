package com.example.Library.Management.System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Condition(int condition_id, double entry_payment, double penalty_cost) {
        this.condition_id = condition_id;
        this.entry_payment = entry_payment;
        this.penalty_cost = penalty_cost;
    }

    public int getCondition_id() {
        return condition_id;
    }

    public void setCondition_id(int condition_id) {
        this.condition_id = condition_id;
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
