package com.spinningnoodle.communitymanager.model.collections;

import static org.junit.jupiter.api.Assertions.*;

import com.spinningnoodle.communitymanager.datastorage.DummyStorage;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import java.security.GeneralSecurityException;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


//TODO rewrite to reflect current MeetupCollection.
class MeetupCollectionTest {
//	private DummyStorage dummyStorage = new DummyStorage("123");
//	private MeetupCollection meetupCollection = new MeetupCollection(dummyStorage);
//
//
//	MeetupCollectionTest() throws GeneralSecurityException {
//	}
//
//	@BeforeEach
//	void setUp() {
//		meetupCollection.clear();
//	}
//
//	@Test
//	void fetchFromDataStorageShouldPopulateTheCollectionFromDatabase() throws GeneralSecurityException {
//		meetupCollection.fetchFromDataStorage();
//
//		assertEquals(dummyStorage.readAll("meetups").size(), meetupCollection.size());
//	}
//
//	@Test
//	void addingAVenueToTheCollectionShouldUpdateTheCollection() {
//		Meetup testMeetup = new Meetup();
//		meetupCollection.addToCollection(testMeetup);
//
//		assertEquals(1, meetupCollection.size());
//	}
//
//	@Test
//	void whenVenueCollectionHasDataThenVenueCanBeRetriedById() throws EntityNotFoundException {
//		Meetup testMeetup = new Meetup();
//		meetupCollection.addToCollection(testMeetup);
//
//		assertEquals(testMeetup, meetupCollection.getById(testMeetup.getMeetupId()));
//	}
//
//	@Test
//	void whenAIdIsPassedToGetByIdThatDoesNotExistThenEntityNotFoundExceptionShouldBeThrown() {
//		assertThrows(EntityNotFoundException.class, () -> meetupCollection.getById(-1));
//	}
//
//	@Test
//	void whenVenueCollectionHasDataThenIShouldBeAbleToGetAllVenues() {
//		int collectionSize = 5;
//
//		IntStream.range(0, collectionSize).mapToObj(i -> new Meetup())
//			.forEach(meetupCollection::addToCollection);
//
//		assertEquals(collectionSize, meetupCollection.getAll().size());
//	}
}