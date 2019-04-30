package com.spinningnoodle.communitymanager.model;
/**
 * LICENSE Copyright (c) 2019 Cream 4 UR Coffee: Kevan Barter, Melanie Felton, Quentin Guenther,
 * Jhakon Pappoe, and Tyler Roemer.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at:
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * END OF LICENSE INFORMATION
 */

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.collections.AdminCollection;
import com.spinningnoodle.communitymanager.model.collections.FoodSponsorCollection;
import com.spinningnoodle.communitymanager.model.collections.MeetupCollection;
import com.spinningnoodle.communitymanager.model.collections.ResponderCollection;
import com.spinningnoodle.communitymanager.model.collections.VenueCollection;
import com.spinningnoodle.communitymanager.model.entities.Admin;
import com.spinningnoodle.communitymanager.model.entities.Entity;
import com.spinningnoodle.communitymanager.model.entities.FoodSponsor;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity.Response;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class GoogleSheetsManager implements DataManager {

    @Autowired
    @Qualifier("admins")
    AdminCollection adminCollection;
    @Autowired
    @Qualifier("meetups")
    MeetupCollection meetupCollection;
    @Autowired
    @Qualifier("venues")
    VenueCollection venueCollection;
    @Autowired
    @Qualifier("food")
    FoodSponsorCollection foodSponsorCollection;
    String spreadsheetIDLocation = "config/SpreadSheetID.txt";

    public GoogleSheetsManager() {
    }

    public GoogleSheetsManager(String storageID) throws GeneralSecurityException, IOException {
//            Map<String,String> config = new HashMap<>();
//            config.put("storage","google");
//            Scanner testIDFile = new Scanner(new File(spreadsheetIDLocation));
//            config.put("storageID",testIDFile.next());
//            if(config.get("storage").equals("google")) {
        DataStorage dataStorage = new GoogleSheets(storageID);
//            }
        adminCollection = new AdminCollection(dataStorage);
        meetupCollection = new MeetupCollection(dataStorage);
        venueCollection = new VenueCollection(dataStorage);
    }

    @Override
    public boolean verifyAdmin(String email) {
        List<Admin> admins = this.getAllAdmins();
        for (Admin admin : admins) {
            if (admin.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Admin> getAllAdmins() {
        adminCollection = adminCollection.fetchFromDataStorage();
        return adminCollection.getAll();
    }

    @Override
    public List<Meetup> getAllMeetups() {
        meetupCollection = meetupCollection.fetchFromDataStorage();
        return meetupCollection.getAll();
    }
    
    @Override
    public List<Meetup> getAllHostedMeetups(){
        List<Meetup> filteredMeetups = new ArrayList<>();
        for(Meetup meetup : meetupCollection.getAll()){
            if (!meetup.getVenue().equals("")){
                filteredMeetups.add(meetup);
            }
        }
        return filteredMeetups;
    }

    //TODO look into possibility of converting to getResponderByToken method
    @Override
    public Venue getVenueByToken(String venueToken) {
        venueCollection = venueCollection.fetchFromDataStorage();
        return venueCollection.getEntityByToken(venueToken);
    }
    
    @Override
    public FoodSponsor getFoodByToken(String venueToken) {
        foodSponsorCollection = foodSponsorCollection.fetchFromDataStorage();
        return foodSponsorCollection.getEntityByToken(venueToken);
    }

    @Override
    public boolean setVenueForMeetup(String venueName, String requestedDate,
        LocalDate dateRequestedByAdmin) {
        meetupCollection = meetupCollection.fetchFromDataStorage();

        if (!requestedDate.equals("notHosting")) {
            LocalDate date = Entity.convertDate(requestedDate);

            if (date.equals(dateRequestedByAdmin)) {
                return meetupCollection.setVenueForMeetup(venueName, date) && venueCollection
                    .updateResponse(venueName, Response.ACCEPTED);
            } else {
                return meetupCollection.setVenueForMeetup(venueName, date);
            }
        } else {
            return venueCollection.updateResponse(venueName, Response.DECLINED);
        }

    }

    @Override
    public boolean setVenueFoodForMeetup(String venueName, String requestedDate,
        LocalDate dateRequestedByAdmin) {
        meetupCollection = meetupCollection.fetchFromDataStorage();

        if (!requestedDate.equals("notHosting")) {
            LocalDate date = Entity.convertDate(requestedDate);

            if (date.equals(dateRequestedByAdmin)) {
                return meetupCollection.setFoodForMeetup(venueName, date) && venueCollection
                    .updateFoodResponse(venueName, Response.ACCEPTED);
            } else {
                return meetupCollection.setFoodForMeetup(venueName, date);
            }
        } else {
            return venueCollection.updateFoodResponse(venueName, Response.DECLINED);
        }

    }
    
    @Override
    public boolean setFoodForMeetup(String foodName, String requestedDate,
        LocalDate dateRequestedByAdmin) {
        meetupCollection = meetupCollection.fetchFromDataStorage();
        
        if (!requestedDate.equals("notHosting")) {
            LocalDate date = Entity.convertDate(requestedDate);
            
            if (date.equals(dateRequestedByAdmin)) {
                return meetupCollection.setFoodForMeetup(foodName, date) && foodSponsorCollection
                    .updateResponse(foodName, Response.ACCEPTED);
            } else {
                return meetupCollection.setFoodForMeetup(foodName, date);
            }
        } else {
            return foodSponsorCollection.updateResponse(foodName, Response.DECLINED);
        }
        
    }

    @Override
    public List<Venue> getAllVenues() {
        venueCollection = venueCollection.fetchFromDataStorage();
        return venueCollection.getAll();
    }

    @Override
    public List<FoodSponsor> getAllFoodSponsors(Meetup meetup) {
        if (!meetup.getVenue().equals("") && meetup.getFood().equals("")) {
            foodSponsorCollection = foodSponsorCollection.fetchFromDataStorage();
            return foodSponsorCollection.getAll();
        }
        return null;
    }

    @Override
    public String requestHost(String primaryKey, LocalDate date) {
        venueCollection = venueCollection.fetchFromDataStorage();
        try {
            int key = Integer.parseInt(primaryKey);
            Venue venue = venueCollection.getByPrimaryKey(key);
            setRequestedDate(venue, date, venueCollection);
            return retrieveToken(venue);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public String requestFood(String primaryKey, LocalDate date) {
        foodSponsorCollection = foodSponsorCollection.fetchFromDataStorage();
        try {
            int key = Integer.parseInt(primaryKey);
            FoodSponsor foodSponsor = foodSponsorCollection.getByPrimaryKey(key);
            setRequestedDate(foodSponsor, date, foodSponsorCollection);
            return retrieveToken(foodSponsor);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getDatabaseAccessPage() {
        return "https://docs.google.com/spreadsheets/d/113AbcCLo0ZAJLhoqP0BXaJPRlzslESkkk98D44Ut1Do/edit#gid=0";
    }
    
    @Override
    public String getMessage(ResponderEntity entity){
        //TODO find out whether or not we need "fetchFromDataStorage" calls here
        
        if(entity instanceof Venue) {
            return venueCollection.getReceiptMessage(meetupCollection.getAll(), entity);
        } else {
            return foodSponsorCollection.getReceiptMessage(meetupCollection.getAll(), entity);
        }
    }

    //TODO Consider possible ways to remove collection argument as collection are already accessible via fields
    private void setRequestedDate(ResponderEntity responder, LocalDate date, ResponderCollection collection) {
        collection.updateResponse(responder.getName(), Response.UNDECIDED);
        collection.updateRequestedDate(responder.getName(), date);
    }

    private String retrieveToken(ResponderEntity responder) {
        return responder.getOrGenerateToken();
    }
}
