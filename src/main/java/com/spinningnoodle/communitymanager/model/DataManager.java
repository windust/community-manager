package com.spinningnoodle.communitymanager.model;

import com.spinningnoodle.communitymanager.model.entities.Meetup;
import java.util.List;
import java.util.Map;

public interface DataManager {
    
    List<Meetup> getAllMeetups();
    
    List<Map<String,String>> getMeetupsByVenueToken(String venueToken);
    
    boolean setVenueForMeetup(String venueName, String requestedDate, String dateRequestedByAdmin);
    
    List<Map<String, String>> getAllVenues();
}
