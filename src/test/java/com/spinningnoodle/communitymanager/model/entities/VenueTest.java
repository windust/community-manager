package com.spinningnoodle.communitymanager.model.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VenueTest {
	private Venue venue;
	private String testName = "Test Venue";
	private String testAddress = "123 Testing St.";

	@BeforeEach
	void setUp() {
		venue = new Venue(testName, testAddress);
	}

	@Test
	void getName() {
		assertEquals(testName, venue.getName());
	}

	@Test
	void setName() {
		String newName = "Testing Coorp.";
		venue.setName(newName);
		assertEquals(newName, venue.getName());
	}

	@Test
	void getAddress() {
		assertEquals(testAddress, venue.getAddress());
	}

	@Test
	void setAddress() {
		String newAddress = "123 New Ave.";
		venue.setAddress(newAddress);
		assertEquals(newAddress, venue.getAddress());
	}

	@Test
	void getVenueId() {
		assertTrue(venue.getVenueId() >= 1);
	}

	@Test
	void setVenueId() {
		int originalId = venue.getVenueId();
		venue.setVenueId();
		assertEquals(originalId + 1, venue.getVenueId());
	}

	@Test
	void eachVenueIncrementsId() {
		Venue newVenue = new Venue();
		assertEquals(newVenue.getVenueId(), venue.getVenueId() + 1);
	}
}