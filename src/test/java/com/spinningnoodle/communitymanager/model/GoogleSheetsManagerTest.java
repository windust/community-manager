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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.DummyStorage;
import com.spinningnoodle.communitymanager.model.collections.DummyMeetupCollection;
import com.spinningnoodle.communitymanager.model.collections.DummyVenueCollection;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class GoogleSheetsManagerTest {

    private static GoogleSheetsManager testManager;
    private static DataStorage testStorage;
    private static List<Map<String, String>> availableDatesMeetups;

    @BeforeAll
    public static void initializeDataBase() throws GeneralSecurityException {
        testManager = new GoogleSheetsManager();
        testStorage = new DummyStorage("123");
        testManager.dataStorage = testStorage;
        testManager.meetupCollection = new DummyMeetupCollection(testStorage);
        testManager.venueCollection = new DummyVenueCollection(testStorage);

        availableDatesMeetups = new ArrayList<>();

        Map<String, String> row = new HashMap<>();
        row.put("name", "Excellent");
        row.put("requestedDate", "01/14/2019");
        availableDatesMeetups.add(row);

        row = new HashMap<>();
        row.put("venue", "Excellent");
        row.put("speaker", "Freddy ");
        row.put("date", "01/14/2019");
        availableDatesMeetups.add(row);

        row = new HashMap<>();
        row.put("venue", "Amazing");
        row.put("speaker", "Nimret ");
        row.put("date", "01/15/2019");
        availableDatesMeetups.add(row);
    }

    @Test
    void whenIUpdateVenueHostUpdateMethodInMeetupCollectionIsCalled() {
        DummyMeetupCollection dummy = (DummyMeetupCollection) testManager.meetupCollection;
        int previousCount = dummy.getTimesSetVenueCalled();
        testManager.setVenueForMeetup("NewName", "01/14/2019");

        int newCount = dummy.getTimesSetVenueCalled();
        System.out.println(previousCount + ":" + newCount);
        assertFalse(previousCount == newCount );
    }

    @Test
    void whenIUpdateVenueHostMethodIReturnWhatIReceived() {
        boolean expected = testManager.meetupCollection.setVenueForMeetup("NewName", "01/14/2019");
        assertEquals(expected, testManager.setVenueForMeetup("NewName", "01/14/2019"));
    }

    @Test
    void whenIGetMeetupsByVenueGetAllMeetupsForTokenIsCalled() {
        DummyMeetupCollection dummy = (DummyMeetupCollection) testManager.meetupCollection;
        int previousCount = dummy.timesGetAllMeetupsForTokenIsCalled;
        testManager.getMeetupByVenueToken("123N");

        int newCount = dummy.timesGetAllMeetupsForTokenIsCalled;
        assertFalse(previousCount == newCount );
    }

    @Test
    void whenIGetMeetupsByVenueTokenIReceiveVenueAndMeetups() {
        List<Map<String,String>> expected = new ArrayList<>();
        Map<String, String> venue = new HashMap<>();
        Map<String, String> meetup = new HashMap<>();
    
        venue.put("name", "Excellent");
        venue.put("requestedDate", "01/14/2019");
        expected.add(venue);
    
        meetup.put("primaryKey", "1");
        meetup.put("date","01/14/2019");
        meetup.put("speaker","Purple");
        meetup.put("topic", "How to do Stuff");
        meetup.put("venue", "Excellent");
        expected.add(meetup);
    
        meetup = new HashMap<>();
        meetup.put("primaryKey", "2");
        meetup.put("date","02/19/2019");
        meetup.put("speaker","Yellow");
        meetup.put("topic", "How to do Stuff");
        meetup.put("venue", "Amazing");
        expected.add(meetup);
    
        meetup = new HashMap<>();
        meetup.put("primaryKey", "3");
        meetup.put("date","03/22/2019");
        meetup.put("speaker","John Doe");
        meetup.put("topic", "How to do Stuff");
        meetup.put("venue", null);
        expected.add(meetup);
        
        
        assertEquals(expected, testManager.getMeetupByVenueToken("123N"));
    }
    
    @Test
    void whenGetAllMeetupsIsCalledCorrectNumberOfMeetupsAreReturned()
        throws IOException {
        assertEquals(testStorage.readAll("meetups").size(), testManager.getAllMeetups().size());
    }
    
    @Test
    void getAllMeetupsReturnsMeetupsWithExpectedAttributes() {
        Map<String, String> meetup = testManager.getAllMeetups().get(0);

        assertAll(() -> {
            assertTrue(meetup.containsKey("date"));
            assertTrue(meetup.containsKey("topic"));
            assertTrue(meetup.containsKey("speaker"));
            assertTrue(meetup.containsKey("venue"));
            assertTrue(meetup.containsKey("primaryKey"));
        });
    }

    
    @Test
    void getAllMeetupsReturnsMeetupsWithExpectedValues() {
        Map<String, String> row = new HashMap<>();
        row.put("primaryKey", "1");
        row.put("date","01/14/2019");
        row.put("speaker","Purple");
        row.put("topic", "How to do Stuff");
        row.put("description", "nailing stuff");
        row.put("venue", "Excellent");

        for(Map<String, String> meetup : testManager.getAllMeetups()){
            if(meetup.get("primaryKey").equals("1")){
                assertAll(() -> {
                    assertEquals(row.get("date"), meetup.get("date"));
                    assertEquals(row.get("topic"), meetup.get("topic"));
                    assertEquals(row.get("speaker"), meetup.get("speaker"));
                    assertEquals(row.get("venue"), meetup.get("venue"));
                    assertEquals(row.get("primaryKey"), meetup.get("primaryKey"));
                });
                return;
            }
        }

        fail("getAllMeetups didn't retrieve proper meetups");
    }

    @Test
    @Disabled("getAllVenues() is not yet implemented")
    void whenGetAllVenuesIsCalledCorrectNumberOfVenuesAreReturned() throws IOException {
        assertEquals(testManager.dataStorage.readAll("venues").size(), testManager.getAllVenues().size());
    }

    @Test
    @Disabled
    void getAllVenuesReturnsVenuesWithExpectedAttributes() {
        Map<String, String> venue = testManager.getAllVenues().get(0);

        assertAll(() -> {
            // TODO: add attribute assertions
        });
    }

    @Test
    @Disabled
    void getAllVenuesReturnsVenuesWithExpectedValues() {
    }

    //TODO
    @Test
    @Disabled
    void whenICreateAGoogleSheetsManagerWithABadSpreadSheetIDFileIGetFileException(){

    }
}