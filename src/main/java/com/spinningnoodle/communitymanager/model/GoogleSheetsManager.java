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
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity.Response;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.List;

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
        meetupCollection = meetupCollection.fetchFromDataStorage();
        return meetupCollection.getAll();
    }

    @Override
    public Venue getVenueByToken(String venueToken){
        venueCollection = venueCollection.fetchFromDataStorage();
        return venueCollection.getEntityByToken(venueToken);
    }

    @Override
    public boolean setVenueForMeetup(String venueName, String requestedDate, LocalDate dateRequestedByAdmin){
        meetupCollection = meetupCollection.fetchFromDataStorage();
        
        if(!requestedDate.equals("notHosting")){
            LocalDate date = convertDate(requestedDate);
            
            if(date.equals(dateRequestedByAdmin)){
                return meetupCollection.setVenueForMeetup(venueName, date) && venueCollection.updateResponse(venueName, Response.ACCEPTED);
            }
            else{
                return meetupCollection.setVenueForMeetup(venueName, date);
            }
        }
        else{
            return venueCollection.updateResponse(venueName, Response.DECLINED);
        }
        
    }
    
    //TODO find way to have this method be accessable from all model files
    protected LocalDate convertDate(String date){
        if(date != null && date != ""){
            String[] dateComponents = date.split("/");
        
            int year = Integer.parseInt(dateComponents[2]);
            int month = Integer.parseInt(dateComponents[0]);
            int day = Integer.parseInt(dateComponents[1]);
        
            return LocalDate.of(year, month, day);
        }
        return null;
    }

    @Override
    public List<Venue> getAllVenues() {
        venueCollection = venueCollection.fetchFromDataStorage();
        return venueCollection.getAll();
    }
    
    public String requestHost(String primaryKey, LocalDate date){
        venueCollection = venueCollection.fetchFromDataStorage();
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
    
    private void setRequestedDate(String venueName, LocalDate date){
        venueCollection.updateResponse(venueName, Response.UNDECIDED);
        venueCollection.updateRequestedDate(venueName, date);
    }

    private String retrieveToken(Venue venue){
            return venue.getOrGenerateToken();
    }
}
