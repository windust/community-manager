package com.spinningnoodle.communitymanager.model;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import com.spinningnoodle.communitymanager.model.collections.MeetupCollection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GoogleSheetsManager {
    private DataStorage dataStorage;
    private MeetupCollection meetupCollection;

    GoogleSheetsManager(){
        try {
            Map<String,String> config = new HashMap<>();
            config.put("storage","google");
            Scanner testIDFile = new Scanner(new File("config/SpreadSheetID.txt"));
            config.put("storageID",testIDFile.next());
            if(config.get("storage").equals("google")) {
                dataStorage = new GoogleSheets(config.get("storageID"));
            }
            meetupCollection = new MeetupCollection(dataStorage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String,String>> getMeetupByVenueToken(String venueToken){
        return meetupCollection.getAllMeetupsForToken(venueToken);
    }

    public boolean setVenueForMeetup(String venueName, String requestedDate ){
        return meetupCollection.setVenueForMeetup(venueName, requestedDate);
    }



}
