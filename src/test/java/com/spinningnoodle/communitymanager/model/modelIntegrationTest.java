package com.spinningnoodle.communitymanager.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import com.spinningnoodle.communitymanager.model.collections.MeetupCollection;
import com.spinningnoodle.communitymanager.model.collections.VenueCollection;
import com.spinningnoodle.communitymanager.model.entities.Entity;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity.Response;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class modelIntegrationTest {
    private static GoogleSheetsManager testManager;
    private static DataStorage testStorage;
    private static String testID;
    private static List<Map<String, String>> expected;
    private static List<Map<String, String>> expectedVenues;

    @BeforeAll
    public static void initializeDataBase() throws IOException, GeneralSecurityException {
        String fileName = "config/testModelStorageID.txt";
        String spreadsheetName = "Integration Test CM Model";
        File file = new File(fileName);
        if (file.isFile() && file.canRead()) {
            FileInputStream in = new FileInputStream(file);
            Scanner testIDFile = new Scanner(in);
            try {
                testID = testIDFile.next();
                testStorage = new GoogleSheets(testID);
            } finally {
                in.close();
            }
        } else {
//            testStorage = new GoogleSheets(fileName,spreadsheetName);
//            testID = testStorage.getStorageID();
        }

        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> row = new HashMap<>();
        row.put("primaryKey", "1");
        row.put("venue", "Excellent");
        row.put("topic", "100");
        row.put("speaker", "Freddy");
//        row.put("food", "");
//        row.put("after", "");
        row.put("date", "01/14/2019");
        list.add(row);

        row = new HashMap<>();
        row.put("primaryKey", "2");
        row.put("venue", "");
        row.put("topic", "150");
        row.put("speaker", "Nimret");
//        row.put("food", "");
//        row.put("after", "");
        row.put("date", "01/15/2019");
        list.add(row);

        expected = list;

        list = new ArrayList<>();
        row = new HashMap<>();
        row.put("primaryKey", "1");
        row.put("venueName", "Excellent");
        row.put("response", "yes");
        row.put("requestedDate", "01/14/2019");
        list.add(row);

        row = new HashMap<>();
        row.put("primaryKey", "2");
        row.put("venueName", "Amazing");
        row.put("response", "yes");
        row.put("requestedDate", "01/14/2019");
        list.add(row);

        expectedVenues = list;

        testManager = new GoogleSheetsManager();
        testManager.meetupCollection = new MeetupCollection(testStorage);
        testManager.venueCollection = new VenueCollection(testStorage);
        testManager.spreadsheetIDLocation = fileName;

//        resetDatastorage();
    }

    @Test
    @DisplayName("When I get Meetups by venue, I get all meetups, associated venue name, requested date, and response.")
    void whenIgetVenueByTokenIGetVenueNameDateRequestedAndResponse(){
        List<Map<String,String>> expectedAvailableDatesMeetups = new ArrayList<>();

        Map<String, String> row = new HashMap<>();
        row.put("name", "Excellent");
        row.put("requestedDate", "01/14/2019");
        row.put("response","yes");
        expectedAvailableDatesMeetups.add(row);

        row = new HashMap<>();
        row.put("venue", "Excellent");
        row.put("speaker", "Freddy");
        row.put("date", "01/14/2019");
        row.put("topic","100");
        row.put("primaryKey","1");
        row.put("description","Freddy");
        expectedAvailableDatesMeetups.add(row);

        row = new HashMap<>();
        row.put("venue", "");
        row.put("speaker", "Nimret");
        row.put("date", "01/15/2019");
        row.put("topic","150");
        row.put("primaryKey","2");
        row.put("description", "Nimret");
        expectedAvailableDatesMeetups.add(row);

        Venue venueByToken = testManager.getVenueByToken("123N");
        assertEquals(expectedAvailableDatesMeetups.get(0).get("name"),venueByToken.getName());
        assertEquals(expectedAvailableDatesMeetups.get(0).get("requestedDate"), Entity.dateFormat.format(venueByToken.getRequestedHostingDate()));
        assertEquals(Response.ACCEPTED,venueByToken.getResponse());
    }

    @Test
    @DisplayName("Throws IllegalArgumentException, When I get meetups by invalid token.")
    void whenIgetMeetupByVenueTokenWithInvalidTokenThrowsError(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testManager.getVenueByToken("455");
        });
    }

    //Expect these two tests to change when we update the available dates page to accept more than true or false.
    @Test
    @DisplayName("When I set the venue for a hosted event, the venue host is unchanged.")
    void whenISetTheVenueForAnEventWithAVenueTheSystemWontChangeIt(){
       assertEquals(false,testManager.setVenueForMeetup("NewName", "01/14/2019",LocalDate.of(2019,1,14)));
    }

    @Test
    @DisplayName("When I set the venue for an unhosted event, the venue host is filled.")
    void whenISetTheVenueForAnEventWithOutAVenueTheSystemChangesIt(){
        assertEquals(true,testManager.setVenueForMeetup("Amazing", "01/15/2019",LocalDate.of(2019,1,14)));
    }

    @Test
    @DisplayName("Model returns false, when I attempt to set venue to an invalid venue")
    void whenISetTheVenueForAnEventWithInvalidVenueNameThrowsError(){
        assertFalse(testManager.setVenueForMeetup("NeverExisted", "01/14/2019",LocalDate.of(2019,1,14)));
    }

    @Test
    @DisplayName("Model returns false, when I attempt to set venue for invalid event date.")
    void whenISetTheVenueForAnEventWithInvalidEventDateThrowsError(){
        assertFalse(testManager.setVenueForMeetup("Excellent", "01/24/2019",LocalDate.of(2019,1,14)));
    }

    @Test
    @DisplayName("Model returns true, when I venue declines to test.")
    void whenISetTheVenueForAnEventWithNotHostingDateReturnsTrue(){
        assertTrue(testManager.setVenueForMeetup("Excellent", "notHosting",LocalDate.of(2019,1,14)));
    }

    /*
    The following are tests related to the Upcoming Dates Page.
     */
    @Test
    @DisplayName("Model return correct length of list of meetups, when I get All Meetups.")
    void whenIGetAllMeetupsIGetTheExpectedLengthOfListOfMeetups(){
        assertEquals(expected.size(),testManager.getAllMeetups().size());
    }

    @Test
    @DisplayName("Model returns minimum attributes of meetups, when I get All Meetups.")
    void whenIGetAllMeetupsIGetTheExpectedValuesForMeetups(){
        assertAll(
            () -> assertNotNull(testManager.getAllMeetups().get(0).getPrimaryKey() ),
            () -> assertNotNull(testManager.getAllMeetups().get(0).getVenue() ),
            () -> assertNotNull(testManager.getAllMeetups().get(0).getTopic() ),
            () -> assertNotNull(testManager.getAllMeetups().get(0).getSpeaker() ),
            () -> assertNotNull(testManager.getAllMeetups().get(0).getDate())
        );
    }

    @Test
    @DisplayName("Model returns correct dates of meetups, when I get All Meetups.")
    void whenIGetAllMeetupsIGetTheExpectedDatesOfMeetups(){
        ArrayList<String> expectedDates = new ArrayList<>();
        ArrayList<String> actualDates = new ArrayList<>();
        List<Meetup> actualAvailableDatesMeetups = testManager.getAllMeetups();
        for(int i =0; i < expected.size(); i++) {
            expectedDates.add(expected.get(i).get("date"));
        }
        for(int i =0; i < actualAvailableDatesMeetups.size(); i++) {
            actualDates.add(Entity.dateFormat.format(actualAvailableDatesMeetups.get(i).getDate()));
        }
        Collections.sort(expectedDates);
        Collections.sort(actualDates);
        assertEquals(expectedDates, actualDates);
    }

    @Test
    @DisplayName("Model return correct length of list of venues, when I get All Venues.")
    void whenIGetAllVenuesIGetTheExpectedLengthOfListOfVenues(){
        assertEquals(expectedVenues.size(),testManager.getAllVenues().size());
    }

    @Test
    @DisplayName("Model returns minimum attributes of venues, when I get All Venues.")
    void whenIGetAllVenuesIGetTheExpectedListOfVenues(){
        assertNotNull(testManager.getAllVenues().get(0).getPrimaryKey() );
        assertNotNull(testManager.getAllVenues().get(0).getName() );
        assertNotNull(testManager.getAllVenues().get(0).getRequestedHostingDate() );
        assertNotNull(testManager.getAllVenues().get(0).getResponse() );
    }

    @Test
    @DisplayName("Model returns correct requested dates for venues, when I get All Venues.")
    void whenIGetAllVenuesIGetTheExpectedRequestDatesOfVenues(){
        ArrayList<String> expectedDates = new ArrayList<>();
        ArrayList<String> actualDates = new ArrayList<>();
        List<Venue> actualRequestedDatesMeetups = testManager.getAllVenues();
        for(int i =0; i < expectedVenues.size(); i++) {
            expectedDates.add(expectedVenues.get(i).get("requestedDate"));
        }
        for(int i =0; i < actualRequestedDatesMeetups.size(); i++) {
            actualDates.add(Entity.dateFormat.format(actualRequestedDatesMeetups.get(i).getRequestedHostingDate()));
        }
        Collections.sort(expectedDates);
        Collections.sort(actualDates);
        assertEquals(expectedDates, actualDates);
    }

    @Test
    @DisplayName("Returns null, When I retrieve token with invalid primary key.")
    void whenIRetrieveTokenWithInvalidPrimaryKeyReturnsNull(){
           assertEquals(null, testManager.requestHost("455",LocalDate.of(2019,1,14)));
    }

    @Test
    @DisplayName("Returns Token, When I retrieve token with valid primary key.")
    void whenIRetrieveTokenWithValidPrimaryKeyReturnsToken(){
        assertEquals("Amazing-94598d03-b485-46e3-93f6-510f62f5a9af", testManager.requestHost("2",
            LocalDate.of(2019,1,14)));
    }

    @BeforeEach
    @AfterEach
    void resetDatastorage(){
        testManager.meetupCollection = testManager.meetupCollection.fetchFromDataStorage();
        testManager.venueCollection = testManager.venueCollection.fetchFromDataStorage();

        testStorage.update("meetups","2","venue","");
        testStorage.update("meetups","1","venue","Excellent");
        testStorage.update("venues","1","response","yes");


    }

}
