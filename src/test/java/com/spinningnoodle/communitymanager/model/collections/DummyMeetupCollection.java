package com.spinningnoodle.communitymanager.model.collections;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.model.collections.MeetupCollection;
import com.spinningnoodle.communitymanager.model.entities.Meetup;

public class DummyMeetupCollection extends MeetupCollection {

    public DummyMeetupCollection(DataStorage dataStorage) {
        super(dataStorage);
    }

    @Override
    public boolean updateMeetupHost(Meetup meetup, String nameOfVenue){
        return true;
    }
}
