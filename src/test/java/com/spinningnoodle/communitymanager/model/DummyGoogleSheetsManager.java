package com.spinningnoodle.communitymanager.model;

import com.spinningnoodle.communitymanager.datastorage.DummyStorage;
import com.spinningnoodle.communitymanager.model.collections.DummyMeetupCollection;
import java.security.GeneralSecurityException;

public class DummyGoogleSheetsManager extends GoogleSheetsManager {
    public DummyGoogleSheetsManager(){
        try{
            dataStorage = new DummyStorage("123");
        } catch (GeneralSecurityException e){
            System.out.println("Data Storage Error");
        }
        meetupCollection = new DummyMeetupCollection(dataStorage);
    }
}
