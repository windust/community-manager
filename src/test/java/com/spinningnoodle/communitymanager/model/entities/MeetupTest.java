package com.spinningnoodle.communitymanager.model.entities;

import static org.junit.jupiter.api.Assertions.*;

import com.spinningnoodle.communitymanager.exceptions.UnexpectedPrimaryKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MeetupTest {
	private Meetup meetup;
	private final int testPrimaryKey = 1;
	private final String testDate = "01/01/1970";
	private final String testSpeaker = "Jane Doe";
	private final String testTopic = "Java programming";
	private final String testDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed haec quidem liberius ab eo dicuntur et saepius.";
	private final String testVenue = "Test Venue";
	//private final Venue testVenue = new Venue();

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

	@Test
	void canGetVenue() {
		assertEquals(testVenue, meetup.getVenue());
	}

//	@Test
//	void canSetVenue() {
//		Venue newVenue = new Venue();
//		meetup.setVenue(newVenue);
//		assertEquals(newVenue, meetup.getVenue());
//	}

//	@Nested
//	class BuildNewMeetupFromMapOfFieldsTest {
//		private Meetup builtMeetup;
//		private Map<String, String> fields = new HashMap<>();
//
//		@BeforeEach
//		void setUp() {
//			fields.put("primaryKey", Integer.toString(testPrimaryKey));
//			fields.put("date", testDate);
//			fields.put("speaker", testSpeaker);
//			fields.put("topic", testTopic);
//			fields.put("description", testDescription);
//
//			builtMeetup = new Meetup().build(fields);
//		}
//
//		@Test
//		void buildingMeetupSetsPrimaryKey() {
//			assertEquals(Integer.parseInt(fields.get("primaryKey")), builtMeetup.getPrimaryKey());
//		}
//
//		@Test
//		void buildingMeetupSetsDate() {
//			assertEquals(testDate, builtMeetup.getDate());
//		}
//
//		@Test
//		void buildingMeetupSetsSpeaker() {
//			assertEquals(testSpeaker, builtMeetup.getSpeaker());
//		}
//
//		@Test
//		void buildingMeetupSetsTopic() {
//			assertEquals(testTopic, builtMeetup.getTopic());
//		}
//
//		@Test
//		void buildingMeetupSetsDescription() {
//			assertEquals(testDescription, builtMeetup.getDescription());
//		}
//
//		@Nested
//		class BuildMeetupWithNoFieldsTest {
//			@BeforeEach
//			void setUp() {
//				builtMeetup = new Meetup().build(new HashMap<>());
//			}
//
//			@Test
//			void whenIBuildAVenueWithNoFieldsThenPrimaryKeyShouldBeNegativeOne() {
//				assertEquals(-1, builtMeetup.getPrimaryKey());
//			}
//
//			@Test
//			void whenIBuildAMeetupWithNoFieldsThenDateShouldBeNull() {
//				assertNull(builtMeetup.getDate());
//			}
//
//			@Test
//			void whenIBuildAMeetupWithNoFieldsThenSpeakerShouldBeNull() {
//				assertNull(builtMeetup.getSpeaker());
//			}
//
//			@Test
//			void whenIBuildAMeetupWithNoFieldsThenTopicShouldBeNull() {
//				assertNull(builtMeetup.getTopic());
//			}
//
//			@Test
//			void whenIBuildAMeetupWithNoFieldsThenDescriptionShouldBeNull() {
//				assertNull(builtMeetup.getDescription());
//			}
//		}
//	}
}