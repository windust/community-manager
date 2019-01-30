package com.spinningnoodle.communitymanager.model.entities;

public class Venue {
    private static int nextId = 1;

    private int venueId;
    private String name;
    private String address;

    public Venue() {
        setVenueId();
    }

    public Venue(String name, String address) {
        this();
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId() {
        this.venueId = nextId;
        ++nextId;
    }
}
