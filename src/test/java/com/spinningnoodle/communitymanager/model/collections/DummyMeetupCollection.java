package com.spinningnoodle.communitymanager.model.collections;

import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.Venue;

public class DummyMeetupCollection extends MeetupCollection {
    public DummyMeetupCollection(){
        Venue venue = new Venue();
        venue.setName("Expedia");
        Meetup unavailableMeetup = new Meetup();
        unavailableMeetup.setVenue(venue);
        Meetup availableMeetup = new Meetup();
        availableMeetup.setDate("2/14/2019");
        
        this.meetup.put(1, unavailableMeetup);
        this.meetup.put(2, availableMeetup);
    }
    
    public Meetup getById(int meetupId) {
        return this.meetup.get(meetupId);
    }
}
