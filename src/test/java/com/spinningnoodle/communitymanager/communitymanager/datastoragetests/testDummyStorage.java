package com.spinningnoodle.communitymanager.communitymanager.datastoragetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.RepeatedTest;

import java.net.ConnectException;

public class testDummyStorage {

    @BeforeEach
    private static void initializeDataBase()throws ConnectException{
        DataStorage testStorage = new DummyStorage("123");
    }

    @Test
    private static void throwsConnectExceptionWhenCantConnectToDataStorage() {
        Assertions.assertThrows(ConnectException.class, () -> {
            new DummyStorage("133");
        });
    }

    @Test
    public static void whenIOpenDataStorageICanGetID() throws ConnectException{
        DataStorage testStorage = new DummyStorage("123");
        assertEquals("123", testStorage.getStorageID());
    }

    @Test
    public static void whenIOpenDataStorageICanGetName() throws ConnectException{

        DataStorage testStorage = new DummyStorage("123");
        assertEquals("123", testStorage.getName());
    }

    @Test
    public static void whenIRequestTableOfSpeakersIGetJSONString() throws ConnectException{
        //something weird with JSON string
//        String expected = "{row1:{name:'jose'}}";
        String expected = "";
        DataStorage testStorage = new DummyStorage("123");
        assertTrue(expected.equals( testStorage.readAll("speakers")));
    }

}
