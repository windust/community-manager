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
import com.spinningnoodle.communitymanager.exceptions.UnexpectedPrimaryKeyException;
import com.spinningnoodle.communitymanager.model.entities.ResponderEntity.Response;
import com.spinningnoodle.communitymanager.model.entities.Venue;
import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * The VenueCollection stores a collection of venues and connect the list to a DataStorage.
 *
 * @author Crean 4 UR Coffee
 * @version 0.1
 */
@Repository(value = "venues")
public class VenueCollection extends FoodSponsorCollection<Venue> { //ResponderCollection<Venue> {

	/**
	 * VenueCollection constructor with a call to it is super
	 * constructor.
	 */
	public VenueCollection(){
		super("venues");
	}

	/**
	 * VenueCollection constructor with a parameter DataStorage
	 * dataStorage and a call to it's super constructor.
	 * @param dataStorage
	 */
	public VenueCollection(DataStorage dataStorage) {
		super(dataStorage, "venues");
	}

	@Override
	public VenueCollection fetchFromDataStorage() {
		try {
			VenueCollection venueCollection = new VenueCollection(this.getDataStorage());
			
			for(Map<String, String> venueFields : getDataStorage().readAll(getTableName())) {
				Venue venue = new Venue();
				try {
					venue.build(venueFields);
				} catch (UnexpectedPrimaryKeyException e) {
					e.printStackTrace();
				}
				venueCollection.addToCollection(venue);
			}
			
			return venueCollection;
		} catch (IOException e) {
			System.out.println("Error: Reading from non-existant table.");
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Update a food sponsors response to hosting
	 *
	 * @param venueName The name of the venue which responded
	 * @param foodResponse The venues response
	 * @return If the dataStorage successfully updated
	 */
	@Override
	public boolean updateFoodResponse(String venueName, Response foodResponse){
		for(Venue venue : getAll()){
			if(venue.getName().equals(venueName)){
				return dataStorageUpdate(getTableName(), Integer.toString(venue.getPrimaryKey()), "foodResponse", foodResponse.getFriendlyName());
			}
		}

		return false;
	}
}