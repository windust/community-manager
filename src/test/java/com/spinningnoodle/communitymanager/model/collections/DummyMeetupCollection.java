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
import java.util.List;
import java.util.Map;

public class DummyMeetupCollection extends MeetupCollection {

    int timesSetVenueCalled = 0;
    public int timesGetAllMeetupsForTokenIsCalled = 0;

    public DummyMeetupCollection(DataStorage dataStorage) {
        super(dataStorage);
        fetchFromDataStorage();
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
    public List<Map<String, String>> getAllMeetupsForToken(String venueToken){
        List<Map<String,String>> list = new ArrayList<>();
        timesGetAllMeetupsForTokenIsCalled++;
        return super.getAllMeetupsForToken(venueToken);
    }

    public int getTimesSetVenueCalled(){
        return timesSetVenueCalled;
    }
}
