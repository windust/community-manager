package com.spinningnoodle.communitymanager.communitymanager.datastoragetests;
//
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
//import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.RepeatedTest;

import java.net.ConnectException;

class testDummyStorage {

    DataStorage testStorage;

    @BeforeEach
    public void initializeDataBase()throws ConnectException{
        testStorage = new DummyStorage("123");
    }

    @Test
    public void throwsConnectExceptionWhenCantConnectToDataStorage() {
        Assertions.assertThrows(ConnectException.class, () -> {
            new DummyStorage("133");
        });
    }

    @Test
    public void whenIOpenDataStorageICanGetID() throws ConnectException{
//        DataStorage testStorage = new DummyStorage("123");
        assertEquals("123", testStorage.getStorageID());
    }

    @Test
    public void whenIOpenDataStorageICanGetName() throws ConnectException{

//        DataStorage testStorage = new DummyStorage("123");
        assertEquals("123", testStorage.getName());
    }

    @Test
    public void whenIRequestTabaleOfSpeakersButTableDoesNotExist(){

    }

    @Test
    public void whenIRequestTableOfSpeakersIGetJSONString() throws ConnectException{
        //something weird with JSON string
        String expected = "{row1:{name:'jose'}}";
//        String expected = "";
//        DataStorage testStorage = new DummyStorage("123");
        assertEquals(expected, testStorage.readAll("speakers"));
//        assertEquals(expected, "speakers");
    }


}
