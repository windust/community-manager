package com.spinningnoodle.communitymanager.model;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.DummyStorage;
import com.spinningnoodle.communitymanager.model.collections.DummyMeetupCollection;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;

public class GoogleSheetsManagerTest {

    private GoogleSheetsManager testManager;

    private DataStorage testStorage;
    private String testID;
    private List<Map<String, String>> availableDatesMeetups;

    @BeforeEach
    public void initializeDataBase() throws IOException, GeneralSecurityException {
        Scanner testIDFile = new Scanner(new File("config/SpreadSheetID.txt"));
        testID = testIDFile.next();

        testManager = new GoogleSheetsManager();
        testManager.dataStorage = new DummyStorage("123");
        testManager.meetupCollection = new DummyMeetupCollection(testManager.dataStorage);

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

    //TODO test needs to be rewritten, currently causes other tests to fail
//    @Test
//    void whenIUpdateVenueHostIGetANewVenueHostBack() {
//        String oldName = "Excellent";
//        testManager.setVenueForMeetup("NewName", "01/14/2019");
//        assertEquals(availableDatesMeetups, testManager.getMeetupByVenueToken("123N"));
//        testManager.setVenueForMeetup(oldName, "01/14/2019");
//    }
}