package com.spinningnoodle.communitymanager.model.entities;

import java.util.Map;

public class Venue implements IEntity {
    private static int nextId = 1;

    private int venueId;
    private int primaryKey;
    private String name;
    private String address;
    private int capacity;
    private String contactPerson;
    private String contactEmail;
    private String contactPhone;
    private String contactAltPhone;
    private String requestedHostingDate;

    @Override
    public Venue build(Map<String, String> fields) {
        Venue venue = new Venue();
        venue.setPrimaryKey(Integer.parseInt(fields.get("primaryKey")));
        venue.setName(fields.get("name"));
        venue.setAddress(fields.get("address"));
        venue.setCapacity(Integer.parseInt(fields.get("capacity")));
        venue.setContactPerson(fields.get("contactPerson"));
        venue.setContactEmail(fields.get("contactEmail"));
        venue.setContactPhone(fields.get("contactPhone"));
        venue.setContactAltPhone(fields.get("contactAltPhone"));
        venue.setRequestedHostingDate(fields.get("requestedHostingDate"));


        return venue;
    }

    public Venue() {
        setVenueId();
    }

    public Venue(int primaryKey, String name, String address, int capacity, String contactPerson,
        String contactEmail, String contactPhone, String contactAltPhone,
        String requestedHostingDate) {
        this();
        this.primaryKey = primaryKey;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.contactPerson = contactPerson;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.contactAltPhone = contactAltPhone;
        this.requestedHostingDate = requestedHostingDate;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactAltPhone() {
        return contactAltPhone;
    }

    public void setContactAltPhone(String contactAltPhone) {
        this.contactAltPhone = contactAltPhone;
    }

    public String getRequestedHostingDate() {
        return requestedHostingDate;
    }

    public void setRequestedHostingDate(String requestedHostingDate) {
        this.requestedHostingDate = requestedHostingDate;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId() {
        this.venueId = nextId;
        ++nextId;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }
}
