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
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.collections.MeetupCollection;
import com.spinningnoodle.communitymanager.model.collections.VenueCollection;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoogleSheetsManager implements DataManager {
    DataStorage dataStorage;
    MeetupCollection meetupCollection;
    VenueCollection venueCollection;
    String spreadsheetIDLocation = "config/SpreadSheetID.txt";

    //TODO rework to remove defualt constructor, currently used for dummy test class
    public GoogleSheetsManager(){}
    
    public GoogleSheetsManager(String storageID) throws GeneralSecurityException, IOException {
//            Map<String,String> config = new HashMap<>();
//            config.put("storage","google");
//            Scanner testIDFile = new Scanner(new File(spreadsheetIDLocation));
//            config.put("storageID",testIDFile.next());
//            if(config.get("storage").equals("google")) {
                dataStorage = new GoogleSheets(storageID);
//            }
            meetupCollection = new MeetupCollection(dataStorage);
            venueCollection = new VenueCollection(dataStorage);
    }

    @Override
    public List<Meetup> getAllMeetups() {
        meetupCollection.fetchFromDataStorage();
        return meetupCollection.getAll();
    }

    @Override
    public List<Map<String,String>> getMeetupsByVenueToken(String venueToken){
//        meetupCollection.fetchFromDataStorage();
//        venueCollection.fetchFromDataStorage();
//        List<Map<String, String>> meetups;
//        meetups = getAllMeetups();
//        meetups.add(0, venueCollection.getVenueFromToken(venueToken));
//        return meetups;
        return null;
    }

    @Override
    public boolean setVenueForMeetup(String venueName, String requestedDate, String dateRequestedByAdmin){
        meetupCollection.fetchFromDataStorage();
        
        if(requestedDate.equals("notHosting")){
            return venueCollection.updateResponse(venueName, "no");
        }
        else if(requestedDate.equals(dateRequestedByAdmin)){
            return meetupCollection.setVenueForMeetup(venueName, requestedDate) && venueCollection.updateResponse(venueName, "yes");
        }
        else{
            return meetupCollection.setVenueForMeetup(venueName, requestedDate);
        }
    }

    @Override
    public List<Map<String, String>> getAllVenues() {
        venueCollection.fetchFromDataStorage();
        List<Venue> venues = venueCollection.getAll();
        List<Map<String, String>> returnValue  = new ArrayList<>();

        for(Venue venue : venues) {
            Map<String, String> venueAttributes = new HashMap<>();

            // TODO: for each venue, add its attributes and values to a map then store the venue map in a list
            venueAttributes.put("requestedDate", venue.getRequestedHostingDate());
            venueAttributes.put("response", venue.getResponse());
            venueAttributes.put("venueName", venue.getName());
            venueAttributes.put("primaryKey", Integer.toString(venue.getPrimaryKey()));
            returnValue.add(venueAttributes);
        }

        return returnValue;
    }
    
    public String requestHost(String primaryKey, String date){
        venueCollection.fetchFromDataStorage();
        try {
            int key = Integer.parseInt(primaryKey);
            Venue venue = venueCollection.getByPrimaryKey(key);
            setRequestedDate(venue.getName(), date);
            return retrieveToken(venue);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void setRequestedDate(String venueName, String date){
        venueCollection.updateResponse(venueName, "");
        venueCollection.updateRequestedDate(venueName, date);
    }

    private String retrieveToken(Venue venue){
            return venue.getOrGenerateToken();
    }
}
