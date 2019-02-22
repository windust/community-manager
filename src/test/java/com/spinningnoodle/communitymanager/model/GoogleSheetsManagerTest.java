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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
import org.junit.jupiter.api.Test;

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
        DummyMeetupCollection dummy = (DummyMeetupCollection) testManager.meetupCollection;
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
    void whenIGetMeetupsByVenueIReturnWhatIReceived() {
        DummyMeetupCollection dummy = (DummyMeetupCollection) testManager.meetupCollection;
        List<Map<String,String>> expected = testManager.meetupCollection.getAllMeetupsForToken("123N");
        assertEquals(expected, testManager.getMeetupByVenueToken("123N") );
    }

    @Test
    void whenICreateAGoogleSheetsManagerWithABadSpreadSheetIDFildIGetFileException(){

    }
}