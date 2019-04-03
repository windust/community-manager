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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.datastorage.DummyStorage;
import com.spinningnoodle.communitymanager.datastorage.GoogleSheets;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.entities.Meetup;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MeetupCollectionTest {

    private DataStorage dataStorage;
    private MeetupCollection meetupCollection;

    @BeforeEach
    void setUp() throws GeneralSecurityException {
        dataStorage = mock(GoogleSheets.class);
        dataStorage = new DummyStorage("123");
        meetupCollection = new MeetupCollection(dataStorage);
    }

    @Test
    void fetchFromDataStorageShouldPopulateTheCollectionFromDatabase()
        throws IOException {
        meetupCollection = meetupCollection.fetchFromDataStorage();
        assertEquals(dataStorage.readAll("meetups").size(), meetupCollection.size());
    }

    @Test
    void addingAVenueToTheCollectionShouldUpdateTheCollection() throws IOException {
        Meetup testMeetup = new Meetup();
        meetupCollection = meetupCollection.fetchFromDataStorage();
        meetupCollection.addToCollection(testMeetup);

        assertEquals(dataStorage.readAll("meetups").size() + 1, meetupCollection.size());
    }

    @Test
    void whenVenueCollectionHasDataThenVenueCanBeRetriedById() throws EntityNotFoundException {
        Meetup testMeetup = new Meetup();
        meetupCollection.addToCollection(testMeetup);

        assertEquals(testMeetup, meetupCollection.getById(testMeetup.getEntityId()));
    }

    @Test
    void whenAIdIsPassedToGetByIdThatDoesNotExistThenEntityNotFoundExceptionShouldBeThrown() {
        assertThrows(EntityNotFoundException.class, () -> meetupCollection.getById(-1));
    }

    @Test
    void whenVenueCollectionHasDataThenIShouldBeAbleToGetAllVenues() throws IOException {
        meetupCollection = meetupCollection.fetchFromDataStorage();
        assertEquals(dataStorage.readAll("meetups").size(), meetupCollection.getAll().size());
    }

    @Test
    void whenAMeetupDoesNotHaveAVenueTheVenueCanBeSet() {
        meetupCollection = meetupCollection.fetchFromDataStorage();
      assertTrue(meetupCollection.setVenueForMeetup("New Venue", LocalDate.of(2019,3,22)));
    }

    @Test
    void whenAMeetupHasAVenueItCantBeOverridden() {
        meetupCollection.setVenueForMeetup("New Venue", LocalDate.of(2019,3,22));
        assertFalse(meetupCollection.setVenueForMeetup("should false", LocalDate.of(2019,3,22)));
    }

    @Test
    void whenAMeetupDoesNotExistForADateReturnFalse() {
        assertFalse(meetupCollection.setVenueForMeetup("should false", LocalDate.of(1,1,1970)));
    }
}