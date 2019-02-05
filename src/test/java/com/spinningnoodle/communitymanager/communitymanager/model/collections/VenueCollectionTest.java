package com.spinningnoodle.communitymanager.communitymanager.model.collections;

import static org.junit.jupiter.api.Assertions.*;

import com.spinningnoodle.communitymanager.communitymanager.datastoragetests.DummyStorage;
import com.spinningnoodle.communitymanager.communitymanager.model.entities.Venue;
import java.security.GeneralSecurityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VenueCollectionTest {

	@BeforeEach
	void setUp() {
		try {
			VenueCollection.fetchFromDataStorage(new DummyStorage("123"));
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}

	@Test
	void getById() throws EntityNotFound {
		Venue testVenue = VenueCollection.getAll().get(1);

		assertEquals(testVenue, VenueCollection.getById(testVenue.getVenueId()));
	}

	@Test
	@DisplayName("Call getById() with a Id that does not exist should throw EntityNotFound exception")
	void getByIdInvalid() {
		assertThrows(EntityNotFound.class, () -> VenueCollection.getById(-1));
	}

	@Test
	void getAll() throws EntityNotFound {
		assertTrue(VenueCollection.getAll().contains(VenueCollection.getById(2)));
	}
}