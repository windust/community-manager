package com.spinningnoodle.communitymanager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class modelIntegrationTest {
    private GoogleSheetsManager testManager;
    private DataStorage testStorage;
    private String testID;
    private List<Map<String, String>> expected;

    @BeforeEach
    public void initializeDataBase() throws IOException, GeneralSecurityException {
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
        row.put("name", "Excellent");
        row.put("address", "100 Nowhere St");
        row.put("capacity", "100");
        row.put("contactPerson", "Freddy");
        row.put("contactEmail", "freddy@excellent.com");
        row.put("contactPhone", "");
        row.put("altContactPhone", "");
        row.put("token", "123N");
        row.put("requestedHostingDate", "01/14/2019");
        list.add(row);

        row = new HashMap<>();
        row.put("primaryKey", "2");
        row.put("name", "Amazing");
        row.put("address", "200 Nowhere St");
        row.put("capacity", "150");
        row.put("contactPerson", "Nimret");
        row.put("contactEmail", "nimret@amazing.com");
        row.put("contactPhone", "");
        row.put("altContactPhone", "");
        row.put("token", "143N");
        row.put("requestedHostingDate", "01/14/2019");
        list.add(row);

        expected = list;

        testManager = new GoogleSheetsManager();
        testManager.dataStorage = testStorage;
        testManager.spreadsheetIDLocation = fileName;

        resetDatastorage();
    }

    @Test
    @DisplayName("When I get Meetups by venue, I get all meetups, associated venue name, and requested date.")
    void whenIgetMeetupByVenueTokenIGetVenueNameDateRequestedAndAllMeetups(){
        List<Map<String,String>> expectedAvailableDatesMeetups = new ArrayList<>();

        Map<String, String> row = new HashMap<>();
        row.put("name", "Excellent");
        row.put("requestedDate", "01/14/2019");
        expectedAvailableDatesMeetups.add(row);

        row = new HashMap<>();
        row.put("venue", "Excellent");
        row.put("speaker", "Freddy ");
        row.put("date", "01/14/2019");
        expectedAvailableDatesMeetups.add(row);

        row = new HashMap<>();
        row.put("venue", "");
        row.put("speaker", "Nimret ");
        row.put("date", "01/15/2019");
        expectedAvailableDatesMeetups.add(row);

        assertEquals(expectedAvailableDatesMeetups,testManager.getMeetupByVenueToken("123N"));
    }

    //We need to figure out what should happen here. Should it throw or ???
    @Test
    @Disabled
    @DisplayName("Throws error, When I get meetups by invalid token.")
    void whenIgetMeetupByVenueTokenWithInvalidTokenThrowsError(){
        Assertions.assertThrows(IOException.class, () -> {
            testManager.getMeetupByVenueToken("455");
        });
    }

    //Expect these two tests to change when we update the available dates page to accept more than true or false.
    @Test
    @Disabled
    @DisplayName("When I set the venue for a hosted event, the venue host is unchanged.")
    void whenISetTheVenueForAnEventWithAVenueTheSystemWontChangeIt(){
       assertEquals(false,testManager.setVenueForMeetup("NewName", "01/14/2019"));
    }

    @Test
    @DisplayName("When I set the venue for an unhosted event, the venue host is filled.")
    void whenISetTheVenueForAnEventWithOutAVenueTheSystemChangesIt(){
        assertEquals(true,testManager.setVenueForMeetup("NewName", "01/15/2019"));
    }

    @Test
    @Disabled
    @DisplayName("Model throws error, when I attempt to set venue to an invalid venue")
    void whenISetTheVenueForAnEventWithInvalidVenueNameThrowsError(){
        Assertions.assertThrows(IOException.class, () -> {
            testManager.setVenueForMeetup("NeverExisted", "01/14/2019");
        });
    }

    @Test
    @Disabled
    @DisplayName("Model throws error, when I attempt to set venue for invalid event date.")
    void whenISetTheVenueForAnEventWithInvalidEventDateThrowsError(){
        Assertions.assertThrows(IOException.class, () -> {
            testManager.setVenueForMeetup("Excellent", "01/24/2019");
        });
    }

    /*
    The following are tests related to the Upcoming Dates Page.
     */
    @Test
    @DisplayName("Model return list of meetups, when I get All Meetups.")
    void whenIGetAllMeetupsIGetTheExpectedListOfMeetups(){
//        assertEquals(expected,testManager.getAllMeetups());
    }

    @Test
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
    @DisplayName("Model throws error, When I get meetup detail for an event with an invalid primary key.")
    void whenIGetMeetupDetailForAnEventWithInvalidPrimaryKeyThrowsError(){
//        Assertions.assertThrows(IOException.class, () -> {
//            testManager.getMeetupDetails("300");
//        });
    }

    @AfterEach
    void resetDatastorage(){
        testManager.setVenueForMeetup("", "01/15/2019");
        testManager.setVenueForMeetup("Excellent", "01/14/2019");
    }

}