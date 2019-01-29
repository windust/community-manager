package com.spinningnoodle.communitymanager.communitymanager.datastoragetests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.RepeatedTest;

public class testDataStorage {

    @Test
    public void whenIOpenDataStorageICanGetID(){
        DataStorage testStorage = new DummyStorage("123");
        assertEquals("123",testStorage.getStorageID());
    }

}
