package com.spinningnoodle.communitymanager.model.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MeetupCollectionTest {

    private DataStorage dataStorage;
    private MeetupCollection meetupCollection;

    @BeforeEach
    void setUp() throws IOException, GeneralSecurityException {
        Map<String, String> config = new HashMap<>();
        config.put("storage", "google");

        Scanner testIDFile = new Scanner(new File("config/SpreadSheetID.txt"));
        config.put("storageID", testIDFile.next());

        if (config.get("storage").equals("google")) {
            dataStorage = new GoogleSheets(config.get("storageID"));
        }
        meetupCollection = new MeetupCollection(dataStorage);

        meetupCollection.clear();
    }

    @Test
    void fetchFromDataStorageShouldPopulateTheCollectionFromDatabase()
        throws IOException {
        meetupCollection.fetchFromDataStorage();

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

        assertEquals(testMeetup, meetupCollection.getById(testMeetup.getEntityId()));
    }

    @Test
    void whenAIdIsPassedToGetByIdThatDoesNotExistThenEntityNotFoundExceptionShouldBeThrown() {
        assertThrows(EntityNotFoundException.class, () -> meetupCollection.getById(-1));
    }

    @Test
    void whenVenueCollectionHasDataThenIShouldBeAbleToGetAllVenues() throws IOException {
        assertEquals(dataStorage.readAll("meetups").size(), meetupCollection.getAll().size());
    }

	/*
	@Test
	void getAllMeetupsForTokenGetsTheVenueByItsTokenIn0Index() {
	}
	@Test
	void getAllMeetupsForTokenGetsAllMeetups() {
		List<Map<String, String>> meetupList =  meetupCollection.getAllMeetupsForToken("");
		try {
			assertEquals(new GoogleSheets());
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
//TODO rewrite to reflect current MeetupCollection.
//
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