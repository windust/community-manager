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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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

//    @Test
//    void whenIGetMeetupsByVenueGetAllMeetupsForTokenIsCalled() {
//        DummyMeetupCollection dummy = (DummyMeetupCollection) googleSheetsManager.meetupCollection;
//        int previousCount = dummy.timesGetAllMeetupsForTokenIsCalled;
//        googleSheetsManager.getMeetupsByVenueToken("123N");
//
//        int newCount = dummy.timesGetAllMeetupsForTokenIsCalled;
//        assertFalse(previousCount == newCount );
//    }
//
//    @Test
//    @Disabled("getAllMeetupsForToken() is not implemented")
//    void whenIGetMeetupsByVenueIReturnWhatIReceived() {
//        //Map<String,String> expected = googleSheetsManager.meetupCollection.getAllMeetupsForToken("123N");
//        //assertEquals(expected, googleSheetsManager.getMeetupsByVenueToken("123N").get(0) );
//    }
//
//    @Test
//    void whenGetAllMeetupsIsCalledCorrectNumberOfMeetupsAreReturned()
//        throws IOException {
//        assertEquals(testStorage.readAll("meetups").size(), googleSheetsManager.getAllMeetups().size());
//    }
//
//    //TODO
//    @Test
//    void getAllMeetupsReturnsMeetupsWithExpectedAttributes() {
//        Map<String, String> meetup = googleSheetsManager.getAllMeetups().get(0);
//
//        assertAll(() -> {
//            assertTrue(meetup.containsKey("date"));
//            assertTrue(meetup.containsKey("topic"));
//            assertTrue(meetup.containsKey("speaker"));
//            assertTrue(meetup.containsKey("venue"));
//            assertTrue(meetup.containsKey("primaryKey"));
//        });
//    }
//
//
//    @Test
//    void getAllMeetupsReturnsMeetupsWithExpectedValues() {
//        Map<String, String> row = new HashMap<>();
//        row.put("primaryKey", "1");
//        row.put("date","01/14/2019");
//        row.put("speaker","Purple");
//        row.put("topic", "How to do Stuff");
//        row.put("description", "nailing stuff");
//        row.put("venue", "Excellent");
//
//        Map<String, String> meetup = googleSheetsManager.getAllMeetups().get(0);
//
//        assertAll(() -> {
//            assertEquals(row.get("date"), meetup.get("date"));
//            assertEquals(row.get("topic"), meetup.get("topic"));
//            assertEquals(row.get("speaker"), meetup.get("speaker"));
//            assertEquals(row.get("venue"), meetup.get("venue"));
//            assertEquals(row.get("primaryKey"), meetup.get("primaryKey"));
//        });
//    }
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