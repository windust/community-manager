package com.spinningnoodle.communitymanager.model;

import com.spinningnoodle.communitymanager.model.entities.Admin;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.time.LocalDate;
import java.util.List;

public interface DataManager {

    boolean verifyAdmin(String email);
    
    List<Admin> getAllAdmins();

    List<Meetup> getAllMeetups();
    
    Venue getVenueByToken(String venueToken);
    
    boolean setVenueForMeetup(String venueName, String requestedDate, LocalDate dateRequestedByAdmin);
    
    List<Venue> getAllVenues();

    String requestHost(String primaryKey, LocalDate date);

    String getDatabaseAccessPage();
}
