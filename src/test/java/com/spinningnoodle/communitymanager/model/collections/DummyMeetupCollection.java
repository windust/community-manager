package com.spinningnoodle.communitymanager.model.collections;
/**
 *  LICENSE
 *  Copyright (c) 2019 Cream 4 UR Coffee: Kevan Barter, Melanie Felton, Quentin Guenther, Jhakon Pappoe, and Tyler Roemer.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at:
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 *  END OF LICENSE INFORMATION
 */

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyMeetupCollection extends MeetupCollection {

    int timesSetVenueCalled = 0;
    public int timesGetAllMeetupsForTokenIsCalled = 0;
    List<Map<String, String>> meetups;

    public DummyMeetupCollection(DataStorage dataStorage) {
        super(dataStorage);
    
        meetups = new ArrayList<>();
        Map<String, String> meetup = new HashMap<>();
    
        meetup.put("primaryKey", "1");
        meetup.put("name","Excellent");
        meetup.put("address","100 Nowhere St");
        meetup.put("capacity", "100");
        meetup.put("contactPerson", "Freddy");
        meetup.put("contactEmail", "freddy@excellent.com");
        meetup.put("contactPhone", "");
        meetup.put("altContactPhone", "");
        meetup.put("token","123N");
        meetup.put("requestedHostingDate", "01/14/2019");
        meetups.add(meetup);
    
        meetup = new HashMap<>();
        meetup.put("primaryKey", "2");
        meetup.put("name","Amazing");
        meetup.put("address","200 Nowhere St");
        meetup.put("capacity", "150");
        meetup.put("contactPerson", "Nimret");
        meetup.put("contactEmail", "nimret@amazing.com");
        meetup.put("contactPhone", "");
        meetup.put("altContactPhone", "");
        meetup.put("token","143N");
        meetup.put("requestedHostingDate", "01/14/2019");
        meetups.add(meetup);
    }

    @Override
    public boolean updateMeetupHost(Meetup meetup, String nameOfVenue){
        return true;
    }

    @Override
    public boolean setVenueForMeetup(String venueName, String requestedDate){
        timesSetVenueCalled++;
        return super.setVenueForMeetup(venueName, requestedDate);
    }

    @Override
    public Map<String, String> getVenueForToken(String venueToken){
        timesGetAllMeetupsForTokenIsCalled++;
    
        Map<String, String> venue = new HashMap<>();
        venue.put("name", "Excellent");
        venue.put("requestedDate", "01/14/2019");
        
        return venue;
    }

    public int getTimesSetVenueCalled(){
        return timesSetVenueCalled;
    }
}
