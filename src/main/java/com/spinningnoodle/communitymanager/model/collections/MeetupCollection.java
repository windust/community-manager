package com.spinningnoodle.communitymanager.model.collections;
/*
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
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
public class MeetupCollection extends EntityCollection<Meetup> {

	/**
	 * @param dataStorage the data storage this object should use as a database
	 */
	public MeetupCollection(DataStorage dataStorage) {
		super(dataStorage, "meetups");
	}

	@Override
	public void fetchFromDataStorage() {
		this.clear();
		try {
			for(Map<String, String> meetupFields : getDataStorage().readAll(getTableName())) {
				Meetup meetup = new Meetup();

				meetup.build(meetupFields);
				addToEntities(meetup);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean updateMeetupHost(Meetup meetup, String nameOfVenue){
		return true;
	}

	@Override
	public void update(Meetup observable) {

	}

	/**
	 * set the venue for a hosting date
	 *
	 * @param venueName The venue name
	 * @param hostingDate The date to be hosted
	 * @return true or false if the DataStore updated
	 */
	public boolean setVenueForMeetup(String venueName, String hostingDate) {
		for(Meetup meetup : getEntitiesValues()) {
			if(meetup.getDate().equals(hostingDate)) {
				return dataStorageUpdate(getTableName(), Integer.toString(meetup.getPrimaryKey()), "venue", venueName);
			}
		}
		return false;
	}

	/**
	 * @param token the token to search
	 * @return a lost of meetups
	 */
	public Map<String, String> getAllMeetupsForToken(String token) {
		this.fetchFromDataStorage();

		return isTokenValid(token);
	}

	// is valid token, get name of venue & requested date
	// is value 'excellent' not valid
	private Map<String, String> isTokenValid(String token) {
		// create venue collection with this datastorage
		VenueCollection venueCollection = new VenueCollection(getDataStorage());
//		venueCollection.fetchFromDataStorage();

		Venue venue = venueCollection.getEntityByToken(token);
		Map<String, String> venueInfo = new HashMap<>();
		venueInfo.put("name", venue.getName());
		venueInfo.put("requestedDate", venue.getRequestedHostingDate());

		return venueInfo;
	}
}