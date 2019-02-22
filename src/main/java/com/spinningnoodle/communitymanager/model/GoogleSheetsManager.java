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
import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import com.spinningnoodle.communitymanager.model.collections.MeetupCollection;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;

public class GoogleSheetsManager {
    DataStorage dataStorage;
    MeetupCollection meetupCollection;
    String spreadsheetIDLocation = "config/SpreadSheetID.txt";

    public GoogleSheetsManager(){
        try {
            Map<String,String> config = new HashMap<>();
            config.put("storage","google");
            Scanner testIDFile = new Scanner(new File(spreadsheetIDLocation));
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

    public List<Map<String, String>> getAllMeetups() {
        List<Meetup> meetups = meetupCollection.getAll();

        List<Map<String, String>> meetupList = new ArrayList<>();

        for(Meetup meetup : meetups) {
            Map<String, String> attributes = new HashMap<>();

            attributes.put("date", meetup.getDate());
            attributes.put("topic", meetup.getTopic());
            attributes.put("speaker", meetup.getSpeaker());
            attributes.put("venue", meetup.getVenue());
            attributes.put("primaryKey", Integer.toString(meetup.getPrimaryKey()));

            meetupList.add(attributes);
        }

        return meetupList;
    }

    public List<Map<String,String>> getMeetupByVenueToken(String venueToken){
        List<Map<String, String>> meetups;
        meetups = getAllMeetups();
        meetups.add(0, meetupCollection.getAllMeetupsForToken(venueToken));
        return meetups;
    }

    public boolean setVenueForMeetup(String venueName, String requestedDate ){
        return meetupCollection.setVenueForMeetup(venueName, requestedDate);
    }



}
