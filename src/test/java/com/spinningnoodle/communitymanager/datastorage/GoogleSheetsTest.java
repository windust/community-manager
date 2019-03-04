package com.spinningnoodle.communitymanager.datastorage;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class GoogleSheetsTest {

    private static DataStorage testStorage;
    private static String testID;
    private static List<Map<String, String>> expected;

    @BeforeAll
    public static void initializeDataBase() throws IOException, GeneralSecurityException {
        String gsFileName = "config/testGSStorageID.txt";
        String spreadsheetName = "Test Google Sheets";
        File file = new File(gsFileName);
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
//            testStorage = new GoogleSheets(gsFileName,spreadsheetName);
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
    }

    @Test
    public void throwsIOExceptionWhenGivenBadStorageID() {
        Assertions.assertThrows(IOException.class, () ->
            new GoogleSheets("133"));
    }

    @Test
    public void whenIOpenDataStorageICanGetID() throws ConnectException {
        assertEquals(testID, testStorage.getStorageID());
    }

    @Test
    public void whenIOpenDataStorageICanGetName() throws ConnectException {
        assertEquals("Test Google Sheets", testStorage.getName());
    }

    @Test
    @Disabled("Unable to access venue table, cause TBD")
    public void whenIRequestTableOfVenuesIShouldGetTheseTwoRows() throws IOException {
        assertEquals(expected, testStorage.readAll("venues"));
    }

    @Test
    public void whenIRequestNonExistentTableIGetIOException() {
        Assertions.assertThrows(IOException.class, () -> {
            testStorage.readAll("sprinklers");
        });
    }

    @Test
    void whenIRequestTableNamesIExpectToGetThreeInAHashMap() {
        HashMap<String,String> tableNames = new HashMap<>();
        tableNames.put("speakers", "1428639487");
        tableNames.put("venues", "0");
        tableNames.put("meetups", "355336406");

        assertEquals(tableNames, testStorage.getTableNames());
    }

    @Test
    void whenISetNameIGetNewName() throws IOException {
        String oldName = testStorage.getName();
        testStorage.setName("NewName");
        assertEquals("NewName", testStorage.getName());
        testStorage.setName(oldName);
    }

    @Test
    void whenIUpdateVenueNameIGetNewVenueNameBack() {
        String oldName = "Amazing";
        String nameAfterChange = "NewName";
        testStorage.update("venues", "2", "name", "NewName");
        try {
            nameAfterChange =  testStorage.readAll("venues").get(0).get("name");
            nameAfterChange =  testStorage.readAll("venues").get(1).get("name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        testStorage.update("venues", "2", "name", "Amazing");
        assertEquals("NewName", nameAfterChange);
    }
}