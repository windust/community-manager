package com.spinningnoodle.communitymanager.modeltest.collections;

import static org.junit.jupiter.api.Assertions.*;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import com.spinningnoodle.communitymanager.datastoragetest.fakes.DummyStorage;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.collections.MeetupCollection;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MeetupCollectionTest {
	private MeetupCollection meetupCollection = new MeetupCollection();

	@BeforeEach
	void setUp() {
		meetupCollection.clear();
	}

	@Test
	void fetchFromDataStorageShouldPopulateTheCollectionFromDatabase()
		throws GeneralSecurityException, IOException {
		Scanner testIDFile = new Scanner(new File("config/SpreadSheetID.txt"));
		String testID = testIDFile.next();

		DataStorage dataStorage = new GoogleSheets(testID);
		meetupCollection.fetchFromDataStorage(dataStorage);

		assertEquals(dataStorage.readAll("meetups").size(), meetupCollection.size());
	}

	@Test
	void addingAVenueToTheCollectionShouldUpdateTheCollection() {
		Meetup testMeetup = new Meetup();
		meetupCollection.addToCollection(testMeetup);

		assertEquals(1, meetupCollection.size());
	}

	@Test
	void whenVenueCollectionHasDataThenVenueCanBeRetriedById() throws EntityNotFoundException {
		Meetup testMeetup = new Meetup();
		meetupCollection.addToCollection(testMeetup);

		assertEquals(testMeetup, meetupCollection.getById(testMeetup.getMeetupId()));
	}

	@Test
	void whenAIdIsPassedToGetByIdThatDoesNotExistThenEntityNotFoundExceptionShouldBeThrown() {
		assertThrows(EntityNotFoundException.class, () -> meetupCollection.getById(-1));
	}

	@Test
	void whenVenueCollectionHasDataThenIShouldBeAbleToGetAllVenues() {
		int collectionSize = 5;

		IntStream.range(0, collectionSize).mapToObj(i -> new Meetup())
			.forEach(meetupCollection::addToCollection);

		assertEquals(collectionSize, meetupCollection.getAll().size());
	}
}