package com.example.Library.Management.System.enums;

public enum PenaltyCost {
    LATEFEE_PERDAY(200.0);

    private final double cost;

    PenaltyCost(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }
}
