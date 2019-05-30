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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GoogleSheetsTest {

    private static GoogleSheets testStorage;
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
        row.put("primaryKey", "2");
        row.put("name", "Excellent");
        row.put("address", "100 Nowhere St");
        row.put("capacity", "100");
        row.put("contactPerson", "Freddy");
        row.put("contactEmail", "freddy@excellent.com");
        row.put("contactPhone", "");
        row.put("altContactPhone", "");
        row.put("token", "123N");
        row.put("requestedDate", "01/14/2019");
        list.add(row);

        row = new HashMap<>();
        row.put("primaryKey", "3");
        row.put("name", "Amazing");
        row.put("address", "200 Nowhere St");
        row.put("capacity", "150");
        row.put("contactPerson", "Nimret");
        row.put("contactEmail", "nimret@amazing.com");
        row.put("contactPhone", "");
        row.put("altContactPhone", "");
        row.put("token", "143N");
        row.put("requestedDate", "01/14/2019");
        list.add(row);

        expected = list;
    }

    @Test
    @DisplayName("Throws IOException when given bad StorageID")
    public void throwsIOExceptionWhenGivenBadStorageID() {
        assertThrows(IOException.class, () ->
            new GoogleSheets("133"));
    }

    @Test
    @DisplayName("Can get StorageID from open DataStorage")
    public void whenIOpenDataStorageICanGetID() throws ConnectException {
        assertEquals(testID, testStorage.getStorageID());
    }

    @Test
    @DisplayName("Can get Name from open DataStorage")
    public void whenIOpenDataStorageICanGetName() throws ConnectException {
        assertEquals("Test Google Sheets", testStorage.getName());
    }

    @Test
    @DisplayName("Can retrieve rows from Venues Table")
    public void whenIRequestTableOfVenuesIShouldGetTheseTwoRows() throws IOException {
        assertEquals(expected, testStorage.readAll("venues"));
    }

    @Test
    @DisplayName("Get IOException when request non-existant table")
    public void whenIRequestNonExistentTableIGetIOException() {
        assertThrows(IOException.class, () -> {
            testStorage.readAll("sprinklers");
        });
    }

    @Test
    @DisplayName("Get 3 table names when call getTableNames")
    void whenIRequestTableNamesIExpectToGetThreeInAHashMap() {
        HashMap<String,String> tableNames = new HashMap<>();
        tableNames.put("speakers", "1428639487");
        tableNames.put("venues", "0");
        tableNames.put("meetups", "355336406");

        assertEquals(tableNames, testStorage.getTableNames());
    }

    @Test
    @DisplayName("setName sets new name in google sheets")
    void whenISetNameIGetNewName() throws IOException {
        String oldName = testStorage.getName();
        testStorage.setName("NewName");
        assertEquals("NewName", testStorage.getName());
        testStorage.setName(oldName);
    }

    @Test
    @DisplayName("Updating venue name changes venue name in google sheets")
    void whenIUpdateVenueNameIGetNewVenueNameBack() {
        String oldName = "Amazing";
        String nameAfterChange = "Amazing";
        testStorage.update("venues", "3", "name", "NewName");
        try {
            //nameAfterChange =  testStorage.readAll("venues").get(0).get("name");
            nameAfterChange =  testStorage.readAll("venues").get(1).get("name");
        } catch (IOException e) {
            e.printStackTrace();
        }
        testStorage.update("venues", "3", "name", "Amazing");
        assertEquals("NewName", nameAfterChange);
    }

    @Test
    @DisplayName("createEntry returns false (unimplemented method)")
    void whenIAttemptToCreateEntryIGetFalse(){
        assertFalse(testStorage.createEntry());
    }

    @Test
    @DisplayName("deleteEntry returns false (unimplemented method)")
    void whenIAttemptToDeleteRowIGetFalse(){
        assertFalse(testStorage.deleteEntry("venues", "3"));
    }

    @Test
    @DisplayName("ReadAll returns null if no values exist in spreadsheet")
    void whenNoValuesAreReturnedFromSpreadSheetIGetNullFromReadAll()
        throws IOException, GeneralSecurityException {
        GoogleSheets testStorage = Mockito.spy(new GoogleSheets(testID));
        List<List<Object>> rawRow = new ArrayList<>();
        when(testStorage.getData("venues")).thenReturn(rawRow);
        assertNull(testStorage.readAll("venues"));
    }

    @Test
    @DisplayName("ReadAll return null if given null from spreadsheet")
    void whenNullIsReturnedFromSpreadSheetIGetNullFromReadAll()
        throws IOException, GeneralSecurityException {
        GoogleSheets testStorage = Mockito.spy(new GoogleSheets(testID));
        when(testStorage.getData("venues")).thenReturn(null);
        assertNull(testStorage.readAll("venues"));
    }

    @Test
    @DisplayName("ReadAll return null if given nothing from spreadsheet")
    void whenStringsAreNotReturnedFromSpreadSheetIGetNullFromReadAll()
        throws IOException, GeneralSecurityException {
        List<Map<String,String>> nonStringExpected = new ArrayList<>();

        GoogleSheets testStorage = Mockito.spy(new GoogleSheets(testID));
        List<List<Object>> rawRow = new ArrayList<>();
        List<Object> badRow = new ArrayList<>();
        badRow.add(new Object());
        rawRow.add(badRow);
        when(testStorage.getData("venues")).thenReturn(rawRow);
        assertEquals(nonStringExpected,testStorage.readAll("venues"));
    }

    @Test
    @DisplayName("Update returns false if given invalid primary key")
    void whenIGiveInvalidPrimaryKeyIGetFalseFromUpdate()
        throws IOException, GeneralSecurityException {
        assertFalse(testStorage.update("venues","4","requestedDate","01/14/2019"));
    }

    @Test
    @DisplayName("Update returns false if given invalid attribute (column name)")
    void whenIGiveInvalidAttributeIGetFalseFromUpdate()
        throws IOException, GeneralSecurityException {
        assertFalse(testStorage.update("venues","3","invalidAttribute","01/14/2019"));
    }

    @Test
    @DisplayName("Update returns false if not given values from spreadsheet")
    void whenNoValuesAreReturnedFromSpreadSheetIGetFalseFromUpdate()
        throws IOException, GeneralSecurityException {
        GoogleSheets testStorage = Mockito.spy(new GoogleSheets(testID));
        List<List<Object>> rawRow = new ArrayList<>();
        when(testStorage.getData("venues")).thenReturn(rawRow);
        assertFalse(testStorage.update("venues","2","requestedDate","01/14/2019"));
    }

    @Test
    @DisplayName("Update returns false if given null from spreadsheet")
    void whenNullIsReturnedFromSpreadSheetIGetFalseFromUpdate()
        throws IOException, GeneralSecurityException {
        GoogleSheets testStorage = Mockito.spy(new GoogleSheets(testID));
        List<List<Object>> rawRow = new ArrayList<>();
        when(testStorage.getData("venues")).thenReturn(null);
        assertFalse(testStorage.update("venues","2","requestedDate","01/14/2019"));
    }

    @Test
    @DisplayName("Update returns false if spreadsheet throws an exception")
    void whenExceptionIsThrownFromSpreadSheetIGetFalseFromUpdate()
        throws IOException, GeneralSecurityException {
        GoogleSheets testStorage = Mockito.spy(new GoogleSheets(testID));
        List<List<Object>> rawRow = new ArrayList<>();
        when(testStorage.getData("venues")).thenThrow(new IOException());
        assertFalse(testStorage.update("venues","2","requestedDate","01/14/2019"));
    }

    @Test
    @DisplayName("Update returns false if attempting to update first row (column headers)")
    void whenUpdatingFirstRowThenReturnFalse(){
        assertFalse(testStorage.update("venues", "1", "requestedDate", "01/14/2019"));
    }
}