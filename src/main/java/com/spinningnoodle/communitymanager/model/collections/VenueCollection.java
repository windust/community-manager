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
import com.spinningnoodle.communitymanager.exceptions.EntityNotFoundException;
import com.spinningnoodle.communitymanager.exceptions.UnexpectedPrimaryKeyException;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The VenueCollection stores a collection of venues and connect the list to a DataStorage.
 *
 * @author Crean 4 UR Coffee
 * @version 0.1
 */
public class VenueCollection extends TokenCollection<Venue> {

	public VenueCollection(DataStorage dataStorage) {
		super(dataStorage, "venues");
	}

	@Override
	public void fetchFromDataStorage() {
		this.clear();
		try {
			for(Map<String, String> venueFields : getDataStorage().readAll(getTableName())) {
				Venue venue = new Venue();
				try {
					venue.build(venueFields);
				} catch (UnexpectedPrimaryKeyException e) {
					e.printStackTrace();
				}
				addToEntities(venue);
			}
		} catch (IOException e) {
			System.out.println("Error: Reading from non-existant table.");
			e.printStackTrace();
		}
	}

	/**
	 * Get a venue from this collection based on its token as a Map of attributes
	 *
	 * @param venueToken the token to match
	 * @return A venue represented with a map of attributes
	 */
	public Map<String, String> getVenueFromToken(String venueToken){
		Venue venue = this.getEntityByToken(venueToken);
		Map<String, String> venueInfo = new HashMap<>();
		venueInfo.put("name", venue.getName());
		venueInfo.put("requestedDate", venue.getRequestedHostingDate());
		venueInfo.put("response", venue.getResponse());
		
		return venueInfo;
	}

	/**
	 * Update a venues response to hosting
	 *
	 * @param venueName The name of the venue which responded
	 * @param response The venues response
	 * @return If the dataStorage successfully updated
	 */
	public boolean updateResponse(String venueName, String response){
		for(Venue venue : getAll()){
			if(venue.getName().equals(venueName)){
				return dataStorageUpdate(getTableName(), Integer.toString(venue.getPrimaryKey()), "response", response);
			}
		}
		
		return false;
	}

	/**
	 * Update the date the venue has requested to host
	 *
	 * @param venueName The venue which has requested a date
	 * @param date The date the venue requested
	 * @return If the dataStorage was successfully updated
	 */
	public boolean updateRequestedDate(String venueName, String date){
		for(Venue venue : getAll()){
			if(venue.getName().equals(venueName)){
				return dataStorageUpdate(getTableName(), Integer.toString(venue.getPrimaryKey()), "requestedHostingDate", date);
			}
		}
		
		return false;
	}

	/**
	 * gets a venue based on its primary key
	 *
	 * @param key the primary key to search by
	 * @return a Venue object
	 * @throws EntityNotFoundException
	 */
	public Venue getByPrimaryKey(int key) throws EntityNotFoundException{
		for(Venue venue : getEntitiesValues()){
			if(venue.getPrimaryKey() == key){
				return venue;
			}
		}
		
		throw new EntityNotFoundException();
	}

	/*
	public boolean setVenueRequestedHostingDateByVenueName(String venueName, String requestedHostingDate) {
		for(Venue venue : this.entities.values()) {
			if(venue.getName().equals(venueName)) {
				return dataStorage.update(TABLE_NAME, Integer.toString(venue.getPrimaryKey()), "requestedHostingDate", requestedHostingDate);
			}
		}
		return false;
	}
	*/
}