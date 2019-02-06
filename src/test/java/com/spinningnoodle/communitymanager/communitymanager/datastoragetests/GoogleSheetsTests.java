package com.spinningnoodle.communitymanager.communitymanager.datastoragetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;

import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import java.io.File;
import java.io.FileReader;
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

public class GoogleSheetsTests {

    private DataStorage testStorage;
    private String testID;
    private List<Map<String, String>> expected;

    @BeforeEach
    public void initializeDataBase() throws IOException, GeneralSecurityException {
        Scanner testIDFile = new Scanner(new File("config/SpreadSheetID.txt"));
        testID = testIDFile.next();

        testStorage = new GoogleSheets(testID);

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
    public void throwsConnectExceptionWhenCantConnectToDataStorage() {
        Assertions.assertThrows(GeneralSecurityException.class, () -> {
            new GoogleSheets("133");
        });
    }

    @Test
    public void whenIOpenDataStorageICanGetID() throws ConnectException {
        assertEquals(testID, testStorage.getStorageID());
    }

    @Test
    public void whenIOpenDataStorageICanGetName() throws ConnectException {
        assertEquals("SeaJUGSpreadSheet", testStorage.getName());
    }

    @Test
    public void whenIRequestTableOfVenuesIShouldGetTheseTwoRows() throws IOException {
        assertEquals(expected, testStorage.readAll("venues"));
    }

    @Test
    public void whenIRequestNonExistentTableIGetIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            testStorage.readAll("sprinklers");
        });
    }

    @Test
    void whenIRequestTableNamesIExpectToGetThreeAsAnArray() {
        String[] tableNames = {"venues", "speakers", "meetups"};
        Arrays.sort(tableNames);

        String[] actualTableNames = testStorage.getTableNames();
        Arrays.sort(actualTableNames);

        assertTrue(Arrays.equals(tableNames, actualTableNames));
    }

    @Test
    void whenISetNameIGetNewName() throws IOException {
        String oldName = testStorage.getName();

        expected.get(0).put("name","NewName");
        testStorage.setName("NewName");
        assertEquals(expected, testStorage.readAll("venues"));
        testStorage.setName(oldName);
        expected.get(0).put("name",oldName);
    }

    @Test
    void whenIUpdateVenueNameIGetNewVenueNameBack() {
        String oldName = "Excellent";
        testStorage.update("venues", "1", "name", "NewName");
        assertEquals("NewName", testStorage.getName());
        testStorage.update("venues", "1", "name", "Excellent");
    }
}