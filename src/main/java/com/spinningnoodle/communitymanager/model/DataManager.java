package com.spinningnoodle.communitymanager.model;

import com.spinningnoodle.communitymanager.model.entities.Admin;
import com.spinningnoodle.communitymanager.model.entities.FoodSponsor;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.time.LocalDate;
import java.util.List;

public interface DataManager {

    boolean verifyAdmin(String email);
    
    List<Admin> getAllAdmins();

    List<Meetup> getAllMeetups();
    
    List<Meetup> getAllHostedMeetups();
    
    Venue getVenueByToken(String venueToken);
    
    FoodSponsor getFoodByToken(String foodToken);
    
    boolean setVenueForMeetup(String venueName, String requestedDate, LocalDate dateRequestedByAdmin);

    boolean setVenueFoodForMeetup(String venueName, String requestedDate,
        LocalDate dateRequestedByAdmin);
    
    boolean setFoodForMeetup(String foodName, String requestedDate, LocalDate dateRequestedByAdmin);
    
    List<Venue> getAllVenues();

    String requestHost(String primaryKey, LocalDate date);
    
    String requestFood(String primaryKey, LocalDate date);

    String getDatabaseAccessPage();

    List<FoodSponsor> getAllFoodSponsors(Meetup meetupKey);
    
    String getMessage(ResponderEntity entity);
}
