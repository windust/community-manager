package com.spinningnoodle.communitymanager.model;
/**
 *  LICENSE
 *  Copyright (c) 2019 Cream 4 UR Coffee: Kevan Barter, Melanie Felton, Quentin Guenther, Jhakon Pappoe, and Tyler Roemer.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at:
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 *  END OF LICENSE INFORMATION
 */

import com.spinningnoodle.communitymanager.model.collections.MeetupCollection;
import com.spinningnoodle.communitymanager.model.collections.VenueCollection;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GoogleSheetsManagerTest {

    @InjectMocks
    private GoogleSheetsManager googleSheetsManager = new GoogleSheetsManager();


    @Mock
    private VenueCollection venueCollection;

    @Mock
    private MeetupCollection meetupCollection;

    @BeforeEach
    void setUp() {
        venueCollection = mock(VenueCollection.class);
        meetupCollection = mock(MeetupCollection.class);

        googleSheetsManager.meetupCollection = meetupCollection;
        googleSheetsManager.venueCollection = venueCollection;
    }

    @Test
    void whenIUpdateVenueHostUpdateMethodInMeetupCollectionIsCalled() {
        when(meetupCollection.setVenueForMeetup("NewName", "01/14/2019")).thenReturn(true);
        when(venueCollection.updateResponse("01/14/2019", "yes")).thenReturn(true);
        googleSheetsManager.setVenueForMeetup("NewName", "01/14/2019","01/14/2019");
        verify(meetupCollection, atLeastOnce()).setVenueForMeetup("NewName", "01/14/2019");

    }

    @Test
    void whenIUpdateVenueHostMethodIReturnWhatIReceived() {
        when(meetupCollection.setVenueForMeetup("True Venue", "01/14/2019")).thenReturn(true);
        when(meetupCollection.setVenueForMeetup("False Venue", "01/14/2019")).thenReturn(false);

        when(venueCollection.updateResponse("True Venue", "yes")).thenReturn(true);
        when(venueCollection.updateResponse("False Venue", "yes")).thenReturn(true);

        assertAll(() -> {
            assertTrue(googleSheetsManager.setVenueForMeetup("True Venue", "01/14/2019","01/14/2019"));
            assertFalse(googleSheetsManager.setVenueForMeetup("False Venue", "01/14/2019","01/14/2019"));
        });
    }

    @Test
    void whenIGetMeetupsByVenueGetAllMeetupsForTokenIsCalled() {
        googleSheetsManager.getMeetupsByVenueToken("");
        verify(meetupCollection, atLeastOnce()).getAll();
    }

    @Test
    void whenGetAllMeetupsIsCalledCorrectNumberOfMeetupsAreReturned() {
        int listSize = 5;

        when(meetupCollection.getAll()).thenAnswer(new Answer<List<Meetup>>() {
            @Override
            public List<Meetup> answer(InvocationOnMock invocation) throws Throwable {
                List<Meetup> meetups = new ArrayList<>();
                for (int i = 0; i < listSize; i++) {
                    meetups.add(new Meetup());
                }
                return meetups;
            }
        });

        assertEquals(listSize, googleSheetsManager.getAllMeetups().size());
    }

    @Test
    void getAllMeetupsReturnsMeetupsWithExpectedAttributes() {
        when(meetupCollection.getAll()).thenAnswer(new Answer<List<Meetup>>() {
            @Override
            public List<Meetup> answer(InvocationOnMock invocation) throws Throwable {
                List<Meetup> meetups = new ArrayList<>();
                meetups.add(new Meetup());
                return meetups;
            }
        });

        Map<String, String> meetupMap = googleSheetsManager.getAllMeetups().get(0);

        assertAll(() -> {
            assertTrue(meetupMap.containsKey("date"));
            assertTrue(meetupMap.containsKey("topic"));
            assertTrue(meetupMap.containsKey("speaker"));
            assertTrue(meetupMap.containsKey("venue"));
            assertTrue(meetupMap.containsKey("primaryKey"));
        });
    }

    @Test
    void getAllMeetupsReturnsMeetupsWithExpectedValues() {
        Map<String, String> expectedMeetupValues = new HashMap<>();
        expectedMeetupValues.put("primaryKey", "1");
        expectedMeetupValues.put("date","01/14/2019");
        expectedMeetupValues.put("speaker","Purple");
        expectedMeetupValues.put("topic", "How to do Stuff");
        expectedMeetupValues.put("description", "nailing stuff");
        expectedMeetupValues.put("venue", "Excellent");

        when(meetupCollection.getAll()).thenAnswer(new Answer<List<Meetup>>() {
            @Override
            public List<Meetup> answer(InvocationOnMock invocation) throws Throwable {
                List<Meetup> meetups = new ArrayList<>();
                Meetup meetup = new Meetup();
                meetup.setPrimaryKey(Integer.parseInt(expectedMeetupValues.get("primaryKey")));
                meetup.setDate(expectedMeetupValues.get("date"));
                meetup.setSpeaker(expectedMeetupValues.get("speaker"));
                meetup.setTopic(expectedMeetupValues.get("topic"));
                meetup.setDescription(expectedMeetupValues.get("description"));
                meetup.setVenue(expectedMeetupValues.get("venue"));

                meetups.add(meetup);
                return meetups;
            }
        });

        Map<String, String> meetup = googleSheetsManager.getAllMeetups().get(0);

        assertAll(() -> {
            assertEquals(expectedMeetupValues.get("date"), meetup.get("date"));
            assertEquals(expectedMeetupValues.get("topic"), meetup.get("topic"));
            assertEquals(expectedMeetupValues.get("speaker"), meetup.get("speaker"));
            assertEquals(expectedMeetupValues.get("venue"), meetup.get("venue"));
            assertEquals(expectedMeetupValues.get("primaryKey"), meetup.get("primaryKey"));
        });
    }
//
//    @Test
//    void whenGetAllVenuesIsCalledCorrectNumberOfVenuesAreReturned() throws IOException {
//        assertEquals(googleSheetsManager.dataStorage.readAll("venues").size(), googleSheetsManager.getAllVenues().size());
//    }
//
//    @Test
//    @Disabled
//    void getAllVenuesReturnsVenuesWithExpectedAttributes() {
//        Map<String, String> venue = googleSheetsManager.getAllVenues().get(0);
//
//        assertAll(() -> {
//            // TODO: add attribute assertions
//        });
//    }
//
//    @Test
//    @Disabled
//    void getAllVenuesReturnsVenuesWithExpectedValues() {
//    }
//
//    //TODO
//    @Test
//    @Disabled
//    void whenICreateAGoogleSheetsManagerWithABadSpreadSheetIDFileIGetFileException(){
//
//    }
}