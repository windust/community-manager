package com.spinningnoodle.communitymanager.model.entities;

import java.util.Map;

/**
 * Venue Entity
 *
 * Venue stores all information associated with a venue in the application
 *
 * @author Quentin Guenther <qguenther@outlook.com>
 * @version 1.0
 */
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
        venue.setPrimaryKey(Integer.parseInt(fields.getOrDefault("primaryKey", "-1")));
        venue.setName(fields.getOrDefault("name", null));
        venue.setAddress(fields.getOrDefault("address", null));
        venue.setCapacity(Integer.parseInt(fields.getOrDefault("capacity", "0")));
        venue.setContactPerson(fields.getOrDefault("contactPerson", null));
        venue.setContactEmail(fields.getOrDefault("contactEmail", null));
        venue.setContactPhone(fields.getOrDefault("contactPhone", null));
        venue.setContactAltPhone(fields.getOrDefault("contactAltPhone", null));
        venue.setRequestedHostingDate(fields.getOrDefault("requestedHostingDate", null));

        return venue;
    }

	/**
	 * Create a new Venue with a unique generated object ID
	 */
    public Venue() {
        setVenueId();
    }

	/**
	 * Create a new venue with all fields and a unique generated ID
	 *
	 * @param primaryKey The key of the venue stored in the database
	 * @param name The name of the venue (company name)
	 * @param address The address of the venue
	 * @param capacity The amount of people the venue is able to hold
	 * @param contactPerson The name of the person to contact for the venue
	 * @param contactEmail The email the venue can be reached at
	 * @param contactPhone The phone number the venue can be reached at
	 * @param contactAltPhone An alternate phone number which could be used to contact the venue
	 * @param requestedHostingDate The date this venue has requested to host
	 */
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

	/**
	 * @return This venues name (company name)
	 */
    public String getName() {
        return name;
    }

	/**
	 * @param name This venues name (company name)
	 */
    public void setName(String name) {
        this.name = name;
    }

	/**
	 * @return This venue's address
	 */
    public String getAddress() {
        return address;
    }

	/**
	 * @param address This venue's address
	 */
    public void setAddress(String address) {
        this.address = address;
    }

	/**
	 * @return The amount of people this venue can hold
	 */
    public int getCapacity() {
        return capacity;
    }

	/**
	 * @param capacity The amount of people this venue can hold
	 */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

	/**
	 * @return The name of the person to contact for the venue
	 */
    public String getContactPerson() {
        return contactPerson;
    }

	/**
	 * @param contactPerson The name of the person to contact for the venue
	 *
	 */
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

	/**
	 * @return The email the venue can be reached at
	 */
    public String getContactEmail() {
        return contactEmail;
    }

	/**
	 * @param contactEmail The email the venue can be reached at
	 */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

	/**
	 * @return The phone number the venue can be reached at
	 */
    public String getContactPhone() {
        return contactPhone;
    }

	/**
	 * @param contactPhone The phone number the venue can be reached at
	 */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

	/**
	 * @return An alternate phone number which could be used to contact the venue
	 */
    public String getContactAltPhone() {
        return contactAltPhone;
    }

	/**
	 * @param contactAltPhone An alternate phone number which could be used to contact the venue
	 */
    public void setContactAltPhone(String contactAltPhone) {
        this.contactAltPhone = contactAltPhone;
    }

	/**
	 * @return The date this venue has requested to host
	 */
    public String getRequestedHostingDate() {
        return requestedHostingDate;
    }

	/**
	 * @param requestedHostingDate The date this venue has requested to host
	 */
    public void setRequestedHostingDate(String requestedHostingDate) {
        this.requestedHostingDate = requestedHostingDate;
    }

	/**
	 * @return This venues unique ID. NOT ASSOCIATED WITH PRIMARY KEY
	 */
    public int getVenueId() {
        return venueId;
    }

	/**
	 * This venues unique ID. NOT ASSOCIATED WITH PRIMARY KEY
	 */
    public void setVenueId() {
        this.venueId = nextId;
        ++nextId;
    }

	/**
	 * @return The venue's primary key stored in DataStorage
	 */
    public int getPrimaryKey() {
        return primaryKey;
    }

	/**
	 * @param primaryKey he venue's primary key stored in DataStorage
	 */
    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

	@Override
	public String toString() {
		return "Venue{" +
			"venueId=" + venueId +
			", primaryKey=" + primaryKey +
			", name='" + name + '\'' +
			", address='" + address + '\'' +
			", capacity=" + capacity +
			", contactPerson='" + contactPerson + '\'' +
			", contactEmail='" + contactEmail + '\'' +
			", contactPhone='" + contactPhone + '\'' +
			", contactAltPhone='" + contactAltPhone + '\'' +
			", requestedHostingDate='" + requestedHostingDate + '\'' +
			'}';
	}

	private String getFieldValue(Map<String, String> fields, String key) {
		return fields.getOrDefault(key, null);
	}
}
