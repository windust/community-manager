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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.DummyStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

public class ResponderCollectionTest {
    private DummyResponderCollection tokenCollection;
    private DataStorage dummyStorage;
    private final String validToken = "valid";
    private final String invalidToken = "invalid";
    
    @BeforeEach
    public void initializeTokenCollection() throws GeneralSecurityException {
        DataStorage dummyStorage = mock(GoogleSheets.class);
        tokenCollection = new DummyResponderCollection(dummyStorage);
        tokenCollection = tokenCollection.fetchFromDataStorage();
    }

    @Test
    @DisplayName("getEntityByToken() returns entity when token is valid")
    public void returnsEntityWhenTokenIsValid() throws IOException {
        List<Venue> venueList = createVenues();

        for(Venue venue : venueList) {
            tokenCollection.addToCollection(venue);
        }

        Assertions.assertNotNull(tokenCollection.getEntityByToken("123N"));
    }
    
    @Test
    @DisplayName("getEntityByToken() throws IllegalArgumentException when token isn't valid")
    public void throwsExceptionWhenTokenIsInvalid(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> tokenCollection.getEntityByToken(invalidToken));
    }

    private List<Venue> createVenues(){
        List<Venue> venueData = new ArrayList<>();
        Venue venue = new Venue();
        Map<String, String> setToken = new HashMap<>();

        setToken.put("token", "123N");
        venue = venue.build(setToken);
        venue.setPrimaryKey(1);
        venue.setName("Excellent");
        venue.setAddress("100 Nowhere St");
        venue.setCapacity(100);
        venue.setContactPerson("Freddy");
        venue.setContactEmail("freddy@excellent.com");
        venue.setContactPhone("");
        venue.setContactAltPhone("");
        venue.setRequestedHostingDate(LocalDate.of(2019,1,14));
        venueData.add(venue);

        venue = new Venue();
        setToken = new HashMap<>();
        setToken.put("token", "143N");
        venue = venue.build(setToken);
        venue.setPrimaryKey(2);
        venue.setName("Amazing");
        venue.setAddress("200 Nowhere St");
        venue.setCapacity(150);
        venue.setContactPerson("Nimret");
        venue.setContactEmail("nimret@amazing.com");
        venue.setContactPhone("");
        venue.setContactAltPhone("");
        venue.setRequestedHostingDate(LocalDate.of(2019,1,14));
        venueData.add(venue);

        return venueData;
    }
}
