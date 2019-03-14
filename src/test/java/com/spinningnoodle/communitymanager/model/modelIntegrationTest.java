package com.spinningnoodle.communitymanager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import com.spinningnoodle.communitymanager.model.collections.MeetupCollection;
import com.spinningnoodle.communitymanager.model.collections.VenueCollection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class modelIntegrationTest {
    private static GoogleSheetsManager testManager;
    private static DataStorage testStorage;
    private static String testID;
    private static List<Map<String, String>> expected;

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

        testManager = new GoogleSheetsManager();
        testManager.dataStorage = testStorage;
        testManager.meetupCollection = new MeetupCollection(testStorage);
        testManager.venueCollection = new VenueCollection(testStorage);
        testManager.spreadsheetIDLocation = fileName;

//        resetDatastorage();
    }

    @Test
    @DisplayName("When I get Meetups by venue, I get all meetups, associated venue name, requested date, and response.")
    void whenIgetMeetupByVenueTokenIGetVenueNameDateRequestedAndAllMeetups(){
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

        List<Map<String,String>> actualAvailableDatesMeetups = testManager.getMeetupsByVenueToken("123N");
        assertEquals(expectedAvailableDatesMeetups.get(0),actualAvailableDatesMeetups.get(0));

        assertTrue(actualAvailableDatesMeetups.get(2).containsKey("venue"));

        assertEquals(expectedAvailableDatesMeetups.get(1).get("venue"),actualAvailableDatesMeetups.get(1).get("venue"));
        assertEquals(expectedAvailableDatesMeetups.get(1).get("speaker"),actualAvailableDatesMeetups.get(1).get("speaker"));
        assertEquals(expectedAvailableDatesMeetups.get(1).get("date"),actualAvailableDatesMeetups.get(1).get("date"));
        assertEquals(expectedAvailableDatesMeetups.get(1).get("topic"),actualAvailableDatesMeetups.get(1).get("topic"));
        assertEquals(expectedAvailableDatesMeetups.get(1).get("primaryKey"),actualAvailableDatesMeetups.get(1).get("primaryKey"));
    }

    //We need to figure out what should happen here. Should it throw or ???
    @Test
    @Disabled
    @DisplayName("Throws error, When I get meetups by invalid token.")
    void whenIgetMeetupByVenueTokenWithInvalidTokenThrowsError(){
        Assertions.assertThrows(IOException.class, () -> {
            testManager.getMeetupsByVenueToken("455");
        });
    }

    //Expect these two tests to change when we update the available dates page to accept more than true or false.
    @Test
    @DisplayName("When I set the venue for a hosted event, the venue host is unchanged.")
    void whenISetTheVenueForAnEventWithAVenueTheSystemWontChangeIt(){
       assertEquals(false,testManager.setVenueForMeetup("NewName", "01/14/2019","01/14/2019"));
    }

    @Test
    @DisplayName("When I set the venue for an unhosted event, the venue host is filled.")
    void whenISetTheVenueForAnEventWithOutAVenueTheSystemChangesIt(){
        assertEquals(true,testManager.setVenueForMeetup("Amazing", "01/15/2019","01/14/2019"));
    }

    @Test
    @DisplayName("Model returns false, when I attempt to set venue to an invalid venue")
    void whenISetTheVenueForAnEventWithInvalidVenueNameThrowsError(){
        assertFalse(testManager.setVenueForMeetup("NeverExisted", "01/14/2019","01/14/2019"));
    }

    @Test
    @DisplayName("Model returns false, when I attempt to set venue for invalid event date.")
    void whenISetTheVenueForAnEventWithInvalidEventDateThrowsError(){
        assertFalse(testManager.setVenueForMeetup("Excellent", "01/24/2019","01/14/2019"));
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
    void whenIGetAllMeetupsIGetTheExpectedListOfMeetups(){
        assertTrue(testManager.getAllMeetups().get(0).entrySet().containsAll(expected.get(0).entrySet()) );
    }

    @Test
    @DisplayName("Model returns correct dates of meetups, when I get All Meetups.")
    void whenIGetAllMeetupsIGetTheExpectedDatesOfMeetups(){
        ArrayList<String> expectedDates = new ArrayList<>();
        ArrayList<String> actualDates = new ArrayList<>();
        List<Map<String,String>> actualAvailableDatesMeetups = testManager.getAllMeetups();
        for(int i =0; i < expected.size(); i++) {
            expectedDates.add(expected.get(i).get("date"));
        }
        for(int i =0; i < actualAvailableDatesMeetups.size(); i++) {
            actualDates.add(actualAvailableDatesMeetups.get(i).get("date"));
        }
        Collections.sort(expectedDates);
        Collections.sort(actualDates);
        assertEquals(expectedDates, actualDates);
    }

    @Test
    @Disabled
    @DisplayName("Model returns Map of meetup attributes, When I get Meetup Details.")
    void whenIGetMeetupDetailsIGetAMapOfAllMeetupAttributes(){
        Map<String,String> expectedMeetupDetailsForPK2 = new HashMap<>();
        expectedMeetupDetailsForPK2.put("primaryKey", "2");
        expectedMeetupDetailsForPK2.put("date","01/15/2019");
        expectedMeetupDetailsForPK2.put("venue","");
        expectedMeetupDetailsForPK2.put("speaker", "Nimret");
        expectedMeetupDetailsForPK2.put("topic", "150");
        expectedMeetupDetailsForPK2.put("description","Nimret");
        expectedMeetupDetailsForPK2.put("food","");
        expectedMeetupDetailsForPK2.put("after","");
//        assertEquals(expectedMeetupDetailsForPK2,testManager.getMeetupDetails("2"));
    }

    @Test
    @Disabled
    @DisplayName("Model throws error, When I get meetup detail for an event with an invalid primary key.")
    void whenIGetMeetupDetailForAnEventWithInvalidPrimaryKeyThrowsError(){
//        Assertions.assertThrows(IOException.class, () -> {
//            testManager.getMeetupDetails("300");
//        });
    }

    @BeforeEach
    @AfterEach
    void resetDatastorage(){

        testStorage.update("meetups","2","venue","");
        testStorage.update("meetups","1","venue","Excellent");

    }

}
