package com.spinningnoodle.communitymanager.model;
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

import com.spinningnoodle.communitymanager.datastorage.DummyStorage;
import com.spinningnoodle.communitymanager.model.collections.DummyMeetupCollection;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyGoogleSheetsManager extends GoogleSheetsManager {
    public DummyGoogleSheetsManager(){
        try{
            dataStorage = new DummyStorage("123");
        } catch (GeneralSecurityException e){
            System.out.println("Data Storage Error");
        }
        meetupCollection = new DummyMeetupCollection(dataStorage);
    }
    
    @Override
    public List<Map<String, String>> getAllVenues(){
        return null;
    }
    
    @Override
    public List<Map<String, String>> getMeetupsByVenueToken(String token){
        List<Map<String, String>> expected = new ArrayList<>();
        Map<String, String> temp = new HashMap<>();
    
        temp.put("name", "Expedia");
        temp.put("requestedDate", "1/12/2019");
        temp.put("response", "no");
        expected.add(temp);
    
        return expected;
    }
    
    @Override
    public boolean setVenueForMeetup(String venueName, String date, String requestedDate){
        return true;
    }
}
