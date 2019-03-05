package com.spinningnoodle.communitymanager.model;

import java.util.List;
import java.util.Map;

public interface DataManager {
    
    List<Map<String, String>> getAllMeetups();
    
    List<Map<String,String>> getMeetupByVenueToken(String venueToken);
    
    boolean setVenueForMeetup(String venueName, String requestedDate);
    
    List<Map<String, String>> getAllVenues();
}
