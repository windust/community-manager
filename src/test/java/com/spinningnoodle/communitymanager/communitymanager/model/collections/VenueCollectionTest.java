package com.spinningnoodle.communitymanager.communitymanager.model.collections;

import static org.junit.jupiter.api.Assertions.*;

import com.spinningnoodle.communitymanager.communitymanager.datastoragetests.DummyStorage;
import com.spinningnoodle.communitymanager.communitymanager.model.entities.UnexpectedPrimaryKeyException;
import com.spinningnoodle.communitymanager.communitymanager.model.entities.Venue;
import java.security.GeneralSecurityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VenueCollectionTest {
	private Venue testVenue = new Venue();

	@BeforeEach
	void setUp() {
		try {
			VenueCollection.fetchFromDataStorage(new DummyStorage("123"));
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}

		VenueCollection.addToCollection(testVenue);
	}

	@Test
	void whenVenueCollectionHasDataThenWhenAVenueId() throws EntityNotFoundException {
		assertEquals(testVenue, VenueCollection.getById(testVenue.getVenueId()));
	}

	@Test
	void whenAIdIsPassedToGetByIdThatDoesNotExistThenEntityNotFoundExceptionShouldBeThrown() {
		assertThrows(EntityNotFoundException.class, () -> VenueCollection.getById(-1));
	}

	@Test
	void whenVenueCollectionHasDataThenIShouldBeAbleToGetAllVenues() throws GeneralSecurityException {
		DummyStorage dummyStorage = new DummyStorage("123");
		assertEquals(dummyStorage.readAll("venues").size(), VenueCollection.getAll().size());
	}
}