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
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Cream 4 UR Coffee
 * @version 0.1
 */
@Repository(value = "meetups")
public class MeetupCollection extends EntityCollection<Meetup> {

	/**
	 * MeetupCollection constructor with a call to the
	 * super constructor in the ResponderCollection.
	 */
	public MeetupCollection(){
		super("meetups");
	}
	
	/**
	 * @param dataStorage the data storage this object should use as a database
	 */
	public MeetupCollection(DataStorage dataStorage) {
		super(dataStorage, "meetups");
	}

	@Override
	public MeetupCollection fetchFromDataStorage() {
		try {
			MeetupCollection meetupCollection = new MeetupCollection(this.getDataStorage());
			
			for(Map<String, String> meetupFields : getDataStorage().readAll(getTableName())) {
				Meetup meetup = new Meetup();

				meetup.build(meetupFields);

				if(meetup.getDate().isAfter(LocalDate.now())) {
					meetupCollection.addToCollection(meetup);
				}
			}
			
			return meetupCollection;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
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
	public boolean setVenueForMeetup(String venueName, LocalDate hostingDate) {
		for(Meetup meetup : getEntitiesValues()) {
			if(meetup.getDate().equals(hostingDate) && (meetup.getVenue() == null || meetup.getVenue().isBlank())) {
				return dataStorageUpdate(getTableName(), Integer.toString(meetup.getPrimaryKey()), "venue", venueName);
			}
		}
		return false;
	}
	
	public boolean setFoodForMeetup(String foodName, LocalDate hostingDate) {
		for(Meetup meetup : getEntitiesValues()) {
			if(meetup.getDate().equals(hostingDate) && (meetup.getFood() == null || meetup.getFood().isBlank())) {
				return dataStorageUpdate(getTableName(), Integer.toString(meetup.getPrimaryKey()), "food", foodName);
			}
		}
		return false;
	}
}