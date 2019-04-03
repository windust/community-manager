package com.spinningnoodle.communitymanager.model;

import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.time.LocalDate;
import java.util.List;

public interface DataManager {
    
    List<Meetup> getAllMeetups();
    
    Venue getVenueByToken(String venueToken);
    
    boolean setVenueForMeetup(String venueName, String requestedDate, LocalDate dateRequestedByAdmin);
    
    List<Venue> getAllVenues();
}
