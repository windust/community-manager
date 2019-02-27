package com.spinningnoodle.communitymanager.datastorage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DummyStorageTests {

    private DataStorage testStorage;

    @BeforeEach
    void initializeDataBase()throws GeneralSecurityException {
        testStorage = new DummyStorage("123");
    }

    @Test
    void throwsConnectExceptionWhenCantConnectToDataStorage() {
        Assertions.assertThrows(GeneralSecurityException.class, () -> new DummyStorage("133"));
    }

    @Test
    void whenIOpenDataStorageICanGetID() {
        assertEquals("123", testStorage.getStorageID());
    }

    @Test
    void whenIOpenDataStorageICanGetName(){
        assertEquals("123", testStorage.getName());
    }

    @Test
    void whenIRequestTableOfVenuesIShouldGetBackResults(){
        List<Map<String, String>> expected;
        List<Map<String, String>> list = new ArrayList<>();
        Map<String,String> row = new HashMap<>();
        row.put("name","Excellent");
        row.put("address","100 Nowhere St");
        row.put("capacity", "100");
        row.put("contactPerson", "Freddy");
        row.put("contactEmail", "freddy@excellent.com");
        row.put("contactPhone", "");
        row.put("altContactPhone", "");
        row.put("token","123N");
        row.put("requestedHostingDate", "01/14/2019");
        list.add(row);

        row = new HashMap<>();
        row.put("primaryKey", "2");
        row.put("name","Amazing");
        row.put("address","200 Nowhere St");
        row.put("capacity", "150");
        row.put("contactPerson", "Nimret");
        row.put("contactEmail", "nimret@amazing.com");
        row.put("contactPhone", "");
        row.put("altContactPhone", "");
        row.put("token","143N");
        row.put("requestedHostingDate", "01/14/2019");
        list.add(row);

        expected = list;
        assertEquals(expected, testStorage.readAll("speakers"));
    }

    @Test
    void whenIRequestNonExistentTableIGetIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> testStorage.readAll("sprinklers"));
    }
}
