package com.spinningnoodle.communitymanager.controllertest.fakes;

import com.spinningnoodle.communitymanager.model.collections.MeetupCollection;
import com.spinningnoodle.communitymanager.model.entities.Meetup;

public class DummyMeetupCollection extends MeetupCollection {
    @Override
    public boolean updateMeetupHost(Meetup meetup, String nameOfVenue){
        return true;
    }
}
