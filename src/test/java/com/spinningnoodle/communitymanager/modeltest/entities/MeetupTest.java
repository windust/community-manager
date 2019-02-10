package com.spinningnoodle.communitymanager.modeltest.entities;

import static org.junit.jupiter.api.Assertions.*;

import com.spinningnoodle.communitymanager.exceptions.UnexpectedPrimaryKeyException;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MeetupTest {
	private Meetup meetup;
	private final int testPrimaryKey = 1;
	private final String testDate = "01/01/1970";
	private final String testSpeaker = "Jane Doe";
	private final String testTopic = "Java programming";
	private final String testDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed haec quidem liberius ab eo dicuntur et saepius.";
	private final Venue testVenue = new Venue();

	@BeforeEach
	void setUp() {
		meetup = new Meetup(testPrimaryKey, testDate, testSpeaker, testTopic, testDescription, testVenue);
	}

	@Test
	void canGetThePrimaryKey() {
		assertEquals(testPrimaryKey, meetup.getPrimaryKey());
	}

	@Test
	void canSetThePrimaryKey() {
		int newPrimaryKey = testPrimaryKey + 1;
		meetup.setPrimaryKey(newPrimaryKey);
		assertEquals(newPrimaryKey, meetup.getPrimaryKey());
	}

	@Test
	void whenIAttemptToSetThePrimaryKeyToAnUnexpectedNumberThenIGetUnexpectedPrimaryKeyException() {
		int newPrimaryKey = -2;
		assertThrows(UnexpectedPrimaryKeyException.class, () -> meetup.setPrimaryKey(newPrimaryKey));
	}

	@Test
	void canGetItMeetupId() {
		assertTrue(meetup.getMeetupId() >= 1);
	}

	@Test
	void canSetItsMeetupIdToTheNextId() {
		int originalId = meetup.getMeetupId();
		meetup.setMeetupId();
		assertEquals(originalId + 1, meetup.getMeetupId());
	}

	@Test
	void eachMeetupIncrementsIdByOne() {
		Meetup newMeetup = new Meetup();
		assertEquals(newMeetup.getMeetupId(), meetup.getMeetupId() + 1);
	}

	@Test
	void canGetMeetupDate() {
		assertEquals(testDate, meetup.getDate());
	}

	@Test
	void canSetMeetupDate() {
		String newDate = "02/02/2020";
		meetup.setDate(newDate);
		assertEquals(newDate, meetup.getDate());
	}

	@Test
	void catGetSpeaker() {
		assertEquals(testSpeaker, meetup.getSpeaker());
	}

	@Test
	void canSetSpeaker() {
		String newSpeaker = "John Smith";
		meetup.setSpeaker(newSpeaker);
		assertEquals(newSpeaker, meetup.getSpeaker());
	}

	@Test
	void canGetTopic() {
		assertEquals(testTopic, meetup.getTopic());
	}

	@Test
	void canSetTopic() {
		String newTopic = "JUnit Testing";
		meetup.setTopic(newTopic);
		assertEquals(newTopic, meetup.getTopic());
	}

	@Test
	void canGetDescription() {
		assertEquals(testDescription, meetup.getDescription());
	}

	@Test
	void canSetDescription() {
		String newDescription = "This is a updated description";
		meetup.setDescription(newDescription);
		assertEquals(newDescription, meetup.getDescription());
	}
}