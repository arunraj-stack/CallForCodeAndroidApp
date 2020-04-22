package com.example.d_safe.ui.home;

public class Invitation {
    public String id;
    public String name;
    public String duration;
    public int checkinStatus;

    public Invitation(String id, String name, String duration, int checkinStatus) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.checkinStatus = checkinStatus;
    }
}
