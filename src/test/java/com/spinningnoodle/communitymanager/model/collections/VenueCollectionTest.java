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

import com.spinningnoodle.communitymanager.datastorage.DummyStorage;
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity.Response;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VenueCollectionTest {
	private DummyStorage dummyStorage = new DummyStorage("123");
	private VenueCollection venueCollection = new VenueCollection(dummyStorage);

	VenueCollectionTest() throws GeneralSecurityException {
	}

	@BeforeEach
	void setUp() {
		venueCollection = venueCollection.fetchFromDataStorage();
	}

	@Test
	void fetchFromDataStorageShouldPopulateTheCollectionFromDatabase() {
		assertEquals(dummyStorage.readAll("venues").size(), venueCollection.size());
	}

	@Test
	void addingAVenueToTheCollectionShouldUpdateTheCollection() {
		Venue testVenue = new Venue();
		testVenue.setPrimaryKey(15);
		int initialSize = venueCollection.size();
		venueCollection.addToCollection(testVenue);

		assertEquals(initialSize + 1, venueCollection.size());
	}

	@Test
	void whenVenueCollectionHasDataThenVenueCanBeRetriedById() throws EntityNotFoundException {
		Venue testVenue = new Venue(1);
		venueCollection.addToCollection(testVenue);

		assertEquals(testVenue, venueCollection.getByPrimaryKey(testVenue.getPrimaryKey()));
	}

	@Test
	void whenAIdIsPassedToGetByIdThatDoesNotExistThenEntityNotFoundExceptionShouldBeThrown() {
		assertThrows(EntityNotFoundException.class, () -> venueCollection.getByPrimaryKey(-1));
	}

	@Test
	void whenVenueCollectionHasDataThenIShouldBeAbleToGetAllVenues() {
		int collectionSize = 2;
		assertEquals(collectionSize, venueCollection.getAll().size());
	}

	//TODO review commented out code
//	@Test
//    void whenIFetchAVenueByTokenIShouldGetAVenue() {
//      assertNotNull(venueCollection.getVenueFromToken("123N"));
//    }
//
//    @Test
//    void whenIAttemptToFetchVenueByInvalidTokenInvalidTokenException() {
//	  assertThrows(IllegalArgumentException.class, () -> venueCollection.getVenueFromToken("invalid"));
//    }

    @Test
    void whenAVenuesResponseIsSetThenItReturnsTrue() {
	  assertTrue(venueCollection.updateResponse("Excellent", Response.ACCEPTED));
    }

    @Test
    void whenAVenueNameIsIncorrectWhenSettingTheResponseThenFalseIsReturned() {
	  assertFalse(venueCollection.updateResponse("DoesNotExist", Response.ACCEPTED));
    }

  @Test
  void whenAVenueRequestsADateThenItReturnsTrue() {
    assertTrue(venueCollection.updateRequestedDate("Excellent", LocalDate.of(1970,1,1)));
  }

  @Test
  void whenAVenueWithInvalidNameRequestsADateThenItReturnsTrue() {
    assertFalse(venueCollection.updateRequestedDate("DoesNotExist!!!", LocalDate.of(1970,1,1)));
  }

	@Test
	void whenAVenueIsLookedUpByPrimaryKeyItIsReturned() throws EntityNotFoundException {
		assertEquals(1, venueCollection.getByPrimaryKey(1).getPrimaryKey());
	}

	@Test
	void whenAVenueIsLookedUpByPrimaryKeyWhichDoesNotExistExceptionIsThrown() {
		assertThrows(EntityNotFoundException.class, () -> venueCollection.getByPrimaryKey(-1));
	}
}