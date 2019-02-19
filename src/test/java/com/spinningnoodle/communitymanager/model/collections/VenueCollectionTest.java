package com.spinningnoodle.communitymanager.model.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.spinningnoodle.communitymanager.datastorage.DummyStorage;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import java.security.GeneralSecurityException;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VenueCollectionTest {
	private DummyStorage dummyStorage = new DummyStorage("123");
	private VenueCollection venueCollection = new VenueCollection(dummyStorage);

	VenueCollectionTest() throws GeneralSecurityException {
	}

	@BeforeEach
	void setUp() {
		venueCollection.clear();
	}

	@Test
	void fetchFromDataStorageShouldPopulateTheCollectionFromDatabase() throws GeneralSecurityException {
		venueCollection.fetchFromDataStorage();

		assertEquals(dummyStorage.readAll("venues").size(), venueCollection.size());
	}

	@Test
	void addingAVenueToTheCollectionShouldUpdateTheCollection() {
		Venue testVenue = new Venue();
		venueCollection.addToCollection(testVenue);

		assertEquals(1, venueCollection.size());
	}

	@Test
	void whenVenueCollectionHasDataThenVenueCanBeRetriedById() throws EntityNotFoundException {
		Venue testVenue = new Venue();
		venueCollection.addToCollection(testVenue);

		assertEquals(testVenue, venueCollection.getById(testVenue.getEntityId()));
	}

	@Test
	void whenAIdIsPassedToGetByIdThatDoesNotExistThenEntityNotFoundExceptionShouldBeThrown() {
		assertThrows(EntityNotFoundException.class, () -> venueCollection.getById(-1));
	}

	@Test
	void whenVenueCollectionHasDataThenIShouldBeAbleToGetAllVenues() {
		int collectionSize = 5;

		IntStream.range(0, collectionSize).mapToObj(i -> new Venue())
			.forEach(venueCollection::addToCollection);

		assertEquals(collectionSize, venueCollection.getAll().size());
	}
}