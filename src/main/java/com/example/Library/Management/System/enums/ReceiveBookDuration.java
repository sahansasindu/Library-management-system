package com.example.Library.Management.System.enums;

public enum ReceiveBookDuration {

    duraton(2);

    private final int duration;
    private ReceiveBookDuration(int duration) {
        this.duration = duration;
    }
    
    public int getDuration() {
        return duration;
    }
}
