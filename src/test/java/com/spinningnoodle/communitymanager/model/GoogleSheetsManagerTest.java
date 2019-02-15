package com.spinningnoodle.communitymanager.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GoogleSheetsManagerTest {

    private GoogleSheetsManager testManager;

    private DataStorage testStorage;
    private String testID;
    private List<Map<String, String>> availableDatesMeetup;

    @BeforeEach
    public void initializeDataBase() throws IOException, GeneralSecurityException {
        Scanner testIDFile = new Scanner(new File("config/SpreadSheetID.txt"));
        testID = testIDFile.next();

//        testStorage = new GoogleSheets(testID);
        testManager = new GoogleSheetsManager();

        List<Map<String, String>> availableDatesMeetups = new ArrayList<>();

        Map<String, String> row = new HashMap<>();
        row.put("name", "Excellent");
        row.put("requestedDate", "01/14/2019");

        row = new HashMap<>();
        row.put("venue", "Excellent");
        row.put("speaker", "Freddy");
        row.put("date", "01/14/2019");
        availableDatesMeetups.add(row);

        row = new HashMap<>();
        row.put("venue", "Excellent");
        row.put("speaker", "Freddy");
        row.put("date", "01/14/2019");
        availableDatesMeetups.add(row);
    }

//    @Test
//    public void throwsConnectExceptionWhenCantConnectToDataStorage() {
//        Assertions.assertThrows(GeneralSecurityException.class, () -> {
//            new GoogleSheets("133");
//        });
//    }

//    @Test
//    public void whenIOpenDataStorageICanGetID() throws ConnectException {
//        assertEquals(testID, testStorage.getStorageID());
//    }
//
//    @Test
//    public void whenIOpenDataStorageICanGetName() throws ConnectException {
//        assertEquals("SeaJUGSpreadSheet", testStorage.getName());
//    }
//
//    @Test
//    public void whenIRequestTableOfVenuesIShouldGetTheseTwoRows() throws IOException {
//        assertEquals(expected, testStorage.readAll("venues"));
//    }
//
//    @Test
//    public void whenIRequestNonExistentTableIGetIllegalArgumentException() {
//        Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            testStorage.readAll("sprinklers");
//        });
//    }
//
//    @Test
//    void whenIRequestTableNamesIExpectToGetThreeAsAnArray() {
//        String[] tableNames = {"venues", "speakers", "meetups"};
//        Arrays.sort(tableNames);
//
//        String[] actualTableNames = testStorage.getTableNames();
//        Arrays.sort(actualTableNames);
//
//        assertTrue(Arrays.equals(tableNames, actualTableNames));
//    }
//
//    @Test
//    void whenISetNameIGetNewName() throws IOException {
//        String oldName = testStorage.getName();
//
//        expected.get(0).put("name","NewName");
//        testStorage.setName("NewName");
//        assertEquals(expected, testStorage.readAll("venues"));
//        testStorage.setName(oldName);
//        expected.get(0).put("name",oldName);
//    }
//
//    @Test
//    void whenIUpdateVenueNameIGetNewVenueNameBack() {
//        String oldName = "Excellent";
//        testStorage.update("venues", "1", "name", "NewName");
//        assertEquals("NewName", testStorage.getName());
//        testStorage.update("venues", "1", "name", "Excellent");
//    }
    @Test
    void whenIUpdateVenueHostIGetANewVenueHostBack() {
        String oldName = "Excellent";
        testManager.setVenueForMeetup("NewName", "01/14/2019");
        assertEquals(availableDatesMeetup, testManager.getMeetupByVenueToken("1"));
        testManager.setVenueForMeetup(oldName, "01/14/2019");
    }
}