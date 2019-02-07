package com.spinningnoodle.communitymanager.communitymanager.model.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.spinningnoodle.communitymanager.communitymanager.datastoragetests.DummyStorage;
import com.spinningnoodle.communitymanager.communitymanager.model.entities.Venue;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import java.security.GeneralSecurityException;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VenueCollectionTest {
	@BeforeEach
	void setUp() {
		VenueCollection.clear();
	}

	@Test
	void fetchFromDataStorageShouldPopulateTheCollectionFromDatabase() throws GeneralSecurityException {
		DummyStorage dummyStorage = new DummyStorage("123");
		VenueCollection.fetchFromDataStorage(dummyStorage);

		assertEquals(dummyStorage.readAll("venues").size(), VenueCollection.size());
	}

	@Test
	void addingAVenueToTheCollectionShouldUpdateTheCollection() {
		Venue testVenue = new Venue();
		VenueCollection.addToCollection(testVenue);

		assertEquals(1, VenueCollection.size());
	}

	@Test
	void whenVenueCollectionHasDataThenVenueCanBeRetriedById() throws EntityNotFoundException {
		Venue testVenue = new Venue();
		VenueCollection.addToCollection(testVenue);

		assertEquals(testVenue, VenueCollection.getById(testVenue.getVenueId()));
	}

	@Test
	void whenAIdIsPassedToGetByIdThatDoesNotExistThenEntityNotFoundExceptionShouldBeThrown() {
		assertThrows(EntityNotFoundException.class, () -> VenueCollection.getById(-1));
	}

	@Test
	void whenVenueCollectionHasDataThenIShouldBeAbleToGetAllVenues() {
		int collectionSize = 5;

		IntStream.range(0, collectionSize).mapToObj(i -> new Venue())
			.forEach(VenueCollection::addToCollection);

		assertEquals(collectionSize, VenueCollection.getAll().size());
	}
}