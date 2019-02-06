package com.spinningnoodle.communitymanager.communitymanager.model.collections;

import com.spinningnoodle.communitymanager.communitymanager.model.entities.UnexpectedPrimaryKeyException;
import com.spinningnoodle.communitymanager.datastorage.DataStorage;
import com.spinningnoodle.communitymanager.communitymanager.model.entities.Venue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The VenueCollection stores a collection of venues and connect the list to a DataStorage.
 *
 * @author Crean 4 UR Coffee
 * @version 0.1
 */
public class VenueCollection {
	private static final String TABLE_NAME = "venues";
	private static Map<Integer, Venue> venues = new HashMap<>();

	/**
	 * Gets all venues from a DataStorage.
	 *
	 * @param dataStorage The DataStorage used to store venues.
	 */
	public static void fetchFromDataStorage(DataStorage dataStorage)
		throws UnexpectedPrimaryKeyException {
		for(Map<String, String> venueFields : dataStorage.readAll(TABLE_NAME)) {
			Venue venue = new Venue().build(venueFields);
			venues.put(venue.getVenueId(), venue);
		}
	}

	/**
	 * Updates the DataStore with the updated venue.
	 *
	 * @param venue the venue which has been updated
	 * @param dataStorage The DataStorage where the venue exists in memory.
	 */
	public static void updateToken(Venue venue, DataStorage dataStorage) {
		/* TODO: Data integrity checks
		if(!venues.containsKey(venue.getVenueId())) {
			// TODO: Throw an error if venue does not exist in this hashmap
		}
		if(venue.getToken ==  venues.get(venue.getVenueId()).getToken) {
			// Todo: Throw error if venue token was unchanged
		}
		*/
		// TODO: update DatStore with venue's token once Venue can hold a token
		//dataStorage.update(TABLE_NAME, venue.getPrimaryKey(), "Token", venue.getToken);
	}

	/**
	 * Gets a venue from this collection based on it's Venue ID
	 *
	 * @param venueId The venueID of the venue to be retrieves.
	 * @return A venue who's venueId matches the venueId passed in
	 * @throws EntityNotFoundException When venue with the Id passed cannot be found
	 */
	public static Venue getById(int venueId) throws EntityNotFoundException {
		if(!venues.containsKey(venueId)) {
			throw new EntityNotFoundException();
		}
		return venues.get(venueId);
	}

	/**
	 * Gets all Venues.
	 *
	 * @return A List of all venues.
	 */
	public static List<Venue> getAll() {
		return new ArrayList<>(venues.values());
	}
}
