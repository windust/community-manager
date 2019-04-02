package com.spinningnoodle.communitymanager.model;

import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.util.List;
import java.util.Map;

public interface DataManager {
    
    List<Meetup> getAllMeetups();
    
    Venue getVenueByToken(String venueToken);
    
    boolean setVenueForMeetup(String venueName, String requestedDate, String dateRequestedByAdmin);
    
    List<Venue> getAllVenues();
}
